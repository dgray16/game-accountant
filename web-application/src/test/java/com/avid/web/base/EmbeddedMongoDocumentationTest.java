package com.avid.web.base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@Getter
@DataMongoTest
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "local-test")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EnableReactiveMongoRepositories(basePackages = "com.avid.core")
@ComponentScan(basePackages = { "com.avid.core", "com.avid.web.game.v1", "com.avid.web.config.web"})
public abstract class EmbeddedMongoDocumentationTest {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    MongoTemplate mongoTemplate;

    WebTestClient webTestClient;


    @Before
    public void setup() {
        this.webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
                .configureClient()
                .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }

    /**
     * NoSQL database makes possible to forget about foreign keys, so we can freely delete all collections.
     */
    @After
    public void cleanDb() {
        mongoTemplate.getCollectionNames().forEach(mongoTemplate::dropCollection);
    }

}
