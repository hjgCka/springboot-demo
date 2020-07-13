package com.hjg.mongodb.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class MongoConfig {

    @Autowired
    private MongoMultiSettingProperties mongoMultiSettingProperties;

    @Bean
    MongoTemplate primaryMongoTemplate(MongoDbFactory primaryMongoDbFactory) {
        return this.createMongoTemplate(primaryMongoDbFactory);
    }

    private MongoTemplate createMongoTemplate(MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

    @Bean
    MongoDbFactory primaryMongoDbFactory(MongoMultiSettingProperties mongoMultiSettingProperties) {
        return this.createMongoDbFactory(mongoMultiSettingProperties.getCustom());
    }

    private MongoDbFactory createMongoDbFactory(MongoSettingProperties mongoSettingProperties) {

        String[] hosts = mongoSettingProperties.getHosts();
        int[] ports = mongoSettingProperties.getPorts();

        List<ServerAddress> serverAddresses = new ArrayList<>();
        for(int i=0; i<hosts.length; i++) {
            serverAddresses.add(new ServerAddress(hosts[i], ports[i]));
        }

        String username = mongoSettingProperties.getUsername();
        String password = mongoSettingProperties.getPassword();
        String database = mongoSettingProperties.getAuthenticationDatabase();
        MongoCredential credential = MongoCredential.createCredential(username, database, password.toCharArray());

        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyToConnectionPoolSettings(bulder -> bulder.maxSize(10).minSize(5)
                        .maxWaitQueueSize(20)
                        .maxWaitTime(5000, TimeUnit.MILLISECONDS)
                        .maxConnectionLifeTime(1000, TimeUnit.SECONDS)
                        .maxConnectionIdleTime(1000, TimeUnit.SECONDS))
                .applyToClusterSettings(builder -> builder.hosts(serverAddresses))
                .build();

        MongoClient mongoClient = MongoClients.create(settings);

        return new SimpleMongoClientDbFactory(mongoClient, mongoSettingProperties.getDatabase());
    }
}
