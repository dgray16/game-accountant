package com.avid.core.domain.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Set;

/*@Configuration*/
/*@EnableMongoRepositories(basePackages = "com.avid.core.domain.repository")*/
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    public MongoClient mongoClient() {
        return new MongoClient("localhost", 27017);
    }

    @Override
    protected String getDatabaseName() {
        return "game_accountant";
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Set.of("com.avid.core.domain.model");
    }
}
