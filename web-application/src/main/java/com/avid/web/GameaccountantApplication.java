package com.avid.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan(basePackages = "com.avid")
@EnableMongoRepositories(basePackages = "com.avid.core.domain.repository")
@SpringBootApplication
public class GameaccountantApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameaccountantApplication.class, args);
    }
}
