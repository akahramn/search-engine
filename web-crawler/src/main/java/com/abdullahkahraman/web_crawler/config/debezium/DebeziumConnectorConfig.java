package com.abdullahkahraman.web_crawler.config.debezium;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DebeziumConnectorConfig {

    @Value("${db.host}")
    private String dbHost;
    @Value("${db.port}")
    private String dbPort;
    @Value("${db.username}")
    private String postgresUsername;
    @Value("${db.password}")
    private String postgresPassword;
    @Value("${schema.include.list}")
    private String schemaIncludeList;
    @Value("${table.include.list}")
    private String tableIncludeList;

    @Bean
    public io.debezium.config.Configuration postgresConnector() {
        Map<String, String> configMap = new HashMap<>();
        //This sets the name of the Debezium connector instance. It’s used for logging and metrics.
        configMap.put("name", "crawler-cdc-connector");
        //This specifies the Java class for the connector. Debezium uses this to create the connector instance.
        configMap.put("connector.class", "io.debezium.connector.postgresql.PostgresConnector");
        File offsetStorageTempFile = new File("offsets_.dat");
        configMap.put("offset.storage",  "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        configMap.put("offset.storage.file.filename", offsetStorageTempFile.getAbsolutePath());
        configMap.put("offset.flush.interval.ms", "60000");
        //Connect Debezium connector to the source DB
        configMap.put("database.hostname", dbHost);
        configMap.put("database.port", dbPort);
        configMap.put("database.user", postgresUsername);
        configMap.put("database.password", postgresPassword);
        configMap.put("database.dbname", "postgres");
        configMap.put("database.server.name", "postgres");
        configMap.put("plugin.name", "pgoutput");
        configMap.put("schema.include.list", schemaIncludeList);
        configMap.put("table.include.list", tableIncludeList);
        configMap.put("topic.prefix", "cdc_");
        return io.debezium.config.Configuration.from(configMap);
    }
}
