package com.abdullahkahraman.web_crawler.util;

import com.abdullahkahraman.web_crawler.service.OutboxService;
import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static io.debezium.data.Envelope.FieldName.*;
import static io.debezium.data.Envelope.Operation;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Component
public class DebeziumUtil {

    // Bu sinif, Debezium adli bir degisiklik veri yakalama aracinin kullanimini icerir.

    //Bu Executor, arka planda calisan is parcaciklarini yonetmeye ve calistirmaya olanak tanir.
    private final Executor executor = Executors.newSingleThreadExecutor(); // Executor kullanarak bir is parcasi olusturuldu.
    //DebeziumEngine serves as an easy-to-use wrapper around any Debezium connector
    private final DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;
    private final OutboxService outboxService;

    @PostConstruct
    public void start() {
        executor.execute(debeziumEngine); // Veritabani degisikliklerinin asenkron olarak islenmesi icin Executor kullanildi.
    }

    @PreDestroy
    public void stop() throws IOException {
        if (debeziumEngine != null) {
            debeziumEngine.close(); // Uygulama sonlandirildiginda Debezium engine'in duzgun bir sekilde kapatilmasi saglandi.
        }
    }

    public DebeziumUtil(Configuration configuration, OutboxService outboxService) {
        debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(configuration.asProperties())
                .notifying(this::handleEvent)
                .build();
        this.outboxService = outboxService;
    }

    // SourceRecord, Debezium gibi bazi veri degisiklikleri izleme ve yakalama araclarinda kullanilan bir siniftir.
    private void handleEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
        SourceRecord sourceRecord = sourceRecordRecordChangeEvent.record();
        Struct sourceRecordChangeValue= (Struct) sourceRecord.value();

        if (sourceRecordChangeValue != null) {
            Operation operation = Operation.forCode((String) sourceRecordChangeValue.get(OPERATION));

            if (operation != Operation.READ) {
                String record = operation == Operation.DELETE ? BEFORE : AFTER;
                Struct struct = (Struct) sourceRecordChangeValue.get(record);
                Map<String, Object> payload = struct.schema().fields().stream()
                        .map(Field::name)
                        .filter(fieldName -> struct.get(fieldName) != null)
                        .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
                        .collect(toMap(Pair::getKey, Pair::getValue));

                outboxService.debeziumDatabaseChange(payload, operation);
            }
        }
    }
}
