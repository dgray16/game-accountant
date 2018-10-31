package com.avid.web.base;

import com.avid.web.config.GameLinkExtractor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Please, use {@link Mono#block()} to be sure that {@link WebTestClient} will get results.
 */
@Getter
@DataMongoTest
@RunWith(SpringRunner.class)
@ActiveProfiles(value = "local-test-documentation")
@ComponentScan(basePackages = {"com.avid.core", "com.avid.web.game.v1", "com.avid.web.config.web"})
public abstract class EmbeddedMongoDocumentationTest {

    protected static final String LINKS_DESCRIPTION = "Links to other resources";

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GameLinkExtractor gameLinkExtractor;

    private WebTestClient webTestClient;


    protected abstract String getControllerName();

    @Before
    public void setup() {
        this.webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
                .configureClient()
                .filter(WebTestClientRestDocumentation
                        .documentationConfiguration(restDocumentation)
                        .operationPreprocessors().withResponseDefaults(Preprocessors.prettyPrint())
                )
                .build();
    }

    /**
     * NoSQL database makes possible to forget about foreign keys, so we can freely delete all collections.
     */
    @After
    public void cleanDb() {
        mongoTemplate.getCollectionNames().forEach(mongoTemplate::dropCollection);
    }

    protected String generateDocumentName(String methodName) {
     return String.format("%s/%s", getControllerName(), methodName);
    }

    protected <T extends Enum> String generateEnumValuesDescription(T[] values) {
        String valuesAsString = Arrays.stream(values)
                .map(Enum::name)
                .collect(Collectors.joining(", "));

        return String.format("Possible values: [ %s ]", valuesAsString);
    }

}
