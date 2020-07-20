package com.hjg.mongodb;

import com.hjg.custom.mongo.config.EnableCustomMongo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCustomMongo
public class MongodbApp {

    public static void main(String[] args) {
        SpringApplication.run(MongodbApp.class, args);
    }
}
