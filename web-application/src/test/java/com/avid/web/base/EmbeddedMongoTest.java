package com.avid.web.base;

import com.avid.web.GameaccountantApplication;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.reactive.WebTestClientBuilderCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@Getter
@Setter
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
/*@AutoConfigureRestDocs*/
/*@ContextConfiguration(classes = GameaccountantApplication.class)*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class EmbeddedMongoTest {

    /*@Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    ApplicationContext applicationContext;*/

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    MongoTemplate mongoTemplate;

    /*@Before
    public void setUp() {
        this.webTestClient = WebTestClient
                .bindToApplicationContext(applicationContext)
                .configureClient()
                .baseUrl("")
                .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }*/

    /**
     * NoSQL database makes possible to forget about foreign keys, so we can freely delete all collections.
     */
    @After
    public void cleanDb() {
        mongoTemplate.getCollectionNames().forEach(mongoTemplate::dropCollection);
    }

}
