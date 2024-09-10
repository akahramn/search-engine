package com.abdullahkahraman.web_crawler.config.debezium;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebeziumConnectorConfig {

    @Bean
    public io.debezium.config.Configuration configuration() {
        return io.debezium.config.Configuration.create()
                .with("name", "crawler-mysql") // Debezium konektorunun adini belirtir.
                .with("database.server.name", "searchengine") // Kafka tarafinda kullanilacak olan veritabani sunucusunun adini belirtir.
                .with("database.hostname", "localhost") // PostgreSQL veritabaninin ana bilgisayar adini belirtir.
                .with("database.port", "3306") // PostgreSQL veritabaninin baglanti noktasini belirtir.
                .with("database.user", "root") // PostgreSQL veritabani kullanici adini belirtir.
                .with("database.dbname", "searchengine") // PostgreSQL veritabani adini belirtir.
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector") // PostgreSQL veritabani icin kullanilacak Debezium konektorunu belirtir.
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore") // Offset bilgilerinin saklanacagi depo turunu belirtir.
                .with("offset.storage.file.filename", "offset.dat") // Offset bilgilerinin saklanacagi dosyanin yolunu belirtir.
                .with("offset.flush.interval.ms", 60000) // Offset bilgilerinin ne siklikta kaydedilecegini belirtir (milisaniye cinsinden).
                .with("schema.history.internal", "io.debezium.storage.file.history.FileSchemaHistory")
                .with("schema.history.internal.file.filename", "schistory.dat")
                .with("topic.prefix", "test") // Kafka topiclerinin on ekini belirtir.
                .with("decimal.handling.mode", "string") // Ondalik sayilari isleme modunu belirtir.
                .with("wal_level", "logical") // PostgreSQL'in Write-Ahead Logging (WAL) seviyesini belirtir.
                .with("table.include.list", "page") // Izlenmesini istediginiz PostgreSQL tablosunu belirtir.
                .with("tasks.max", "1") // Eszamanli gorev sayisini belirtir.
                .with("tombstones.on.delete", "false") // Silme islemlerini belirli bir sekilde islemenin ayarini belirtir.
                .with("route.topic.regex", "") // Konu yonlendirmesi icin regex deseni belirtir.
                .build();

        //.with("database.password", "toor") // PostgreSQL veritabani kullanici parolasini belirtir.
        // .with("skipped.operations", "t,d") // Atlanan islem turlerini belirtir. // c for inserts/create, u for updates, d for deletes, t for truncates, and none to not skip any operations
    }
}
