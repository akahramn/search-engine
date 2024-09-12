package com.abdullahkahraman.web_crawler.util;

import com.abdullahkahraman.web_crawler.service.OutboxService;
import io.debezium.config.Configuration;
import io.debezium.engine.format.Json;
import io.debezium.embedded.EmbeddedEngine;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static io.debezium.data.Envelope.FieldName.AFTER;
import static io.debezium.data.Envelope.FieldName.OPERATION;

@Slf4j
@Component
public class DebeziumUtil {

    // Bu sinif, Debezium adli bir degisiklik veri yakalama aracinin kullanimini icerir.

    //Bu Executor, arka planda calisan is parcaciklarini yonetmeye ve calistirmaya olanak tanir.
    private final Executor executor = Executors.newSingleThreadExecutor(); // Executor kullanarak bir is parcasi olusturuldu.
    //DebeziumEngine serves as an easy-to-use wrapper around any Debezium connector
    private final DebeziumEngine<ChangeEvent<String, String>> debeziumEngine;
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
        this.debeziumEngine = DebeziumEngine.create(Json.class)
                .using(configuration.asProperties())
                //This is where your CDC events will be passed to
                .notifying(this::handleEvent)
                .build();
        this.outboxService = outboxService;
    }

    // SourceRecord, Debezium gibi bazi veri degisiklikleri izleme ve yakalama araclarinda kullanilan bir siniftir.
    private void handleEvent(ChangeEvent<String, String> event) {
        // Degisiklik verisi islenir ve uygun bir sekilde kaydedilir.
//        Struct sourceRecordValue =  event.value();// Kaynagin (sourceRecord) degerini alir.
//
//        // Degisiklik turunu (create, update, delete vb.) belirlemek icin kaynaktan CRUD operasyonunu al
//        var crudOperation = (String) sourceRecordValue.get(OPERATION);
//
//        //r for read //c for create //u for updates //d for delete
//        // CRUD islemleri kontrol edilir ve degisiklikler uygun sekilde islenir.
//        if (sourceRecordValue != null && (crudOperation == "c" || crudOperation == "u")) {
//            Struct struct = (Struct) sourceRecordValue.get(AFTER);
//            Map<String, Object> payload = struct.schema().fields().stream()
//                    .filter(field -> struct.get(field) != null)
//                    .collect(Collectors.toMap(Field::name, field -> struct.get(field))); // Veri degisikliginin islenmesi ve uygun bir sekilde kaydedilmesi saglandi.
//
//            outboxService.debeziumDatabaseChange(payload); // Is mantigi islemi icin ilgili servis kullanildi.
//        }
    }
}
