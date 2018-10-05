package com.avid.web.base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@Getter
@Setter
/*@RunWith(SpringRunner.class)*/
@ActiveProfiles(value = "local-test")
@FieldDefaults(level = AccessLevel.PRIVATE)
/*@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)*/
public abstract class EmbeddedMongoTest {

    /*@Autowired*/
    private ApplicationContext applicationContext;

    /*@Autowired*/
    MongoTemplate mongoTemplate;

    /*@Autowired*/
    WebTestClient webTestClient;

    /**
     * NoSQL database makes possible to forget about foreign keys, so we can freely delete all collections.
     */
    /*@After*/
    public void cleanDb() {
        mongoTemplate.getCollectionNames().forEach(mongoTemplate::dropCollection);
    }

}
