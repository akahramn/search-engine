package com.abdullahkahraman.web_crawler.config.debezium;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebeziumConnectorConfig {

    @Bean
    public io.debezium.config.Configuration configuration() {
        return io.debezium.config.Configuration.create()
                .with("name", "outbox-postgres") // Debezium konektorunun adini belirtir.
                .with("database.hostname", "localhost") // PostgreSQL veritabaninin ana bilgisayar adini belirtir.
                .with("database.port", "5432") // PostgreSQL veritabaninin baglanti noktasini belirtir.
                .with("database.user", "postgres") // PostgreSQL veritabani kullanici adini belirtir.
                .with("database.password", "toor") // PostgreSQL veritabani kullanici parolasini belirtir.
                .with("database.dbname", "postgres") // PostgreSQL veritabani adini belirtir.
                .with("connector.class", "io.debezium.connector.postgresql.PostgresConnector") // PostgreSQL veritabani icin kullanilacak Debezium konektorunu belirtir.
                .with("database.server.id", "184054")
                .with("topic.prefix", "dbserver1")
                .with("schema.history.internal.kafka.bootstrap.servers", "59092")
                .with("schema.history.internal.kafka.topic", "schema-changes.inventory")
                .with("plugin.name", "pgoutput") // Debezium tarafindan kullanilacak PostgreSQL plugin adini belirtir.
                .with("table.include.list", "postgres") // Izlenmesini istediginiz PostgreSQL tablosunu belirtir.
                .with("tasks.max", "1") // Eszamanli gorev sayisini belirtir.
                .build();

        //.with("database.password", "toor") // PostgreSQL veritabani kullanici parolasini belirtir.
        // .with("skipped.operations", "t,d") // Atlanan islem turlerini belirtir. // c for inserts/create, u for updates, d for deletes, t for truncates, and none to not skip any operations
    }
}
