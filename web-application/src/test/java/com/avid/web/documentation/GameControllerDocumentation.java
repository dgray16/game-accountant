package com.avid.web.documentation;

import com.avid.core.domain.model.entity.Game;
import com.avid.web.RestCustomizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClientConfigurer;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureRestDocs
@RunWith(SpringRunner.class)
public class GameControllerDocumentation {

    @Autowired
    WebTestClient originalWebTestClient;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    RestCustomizer restCustomizer;

    @Test
    public void test() {

        WebTestClient newTestClient = originalWebTestClient.mutateWith((builder, httpHandlerBuilder, connector) ->
                restCustomizer.customize(builder));

        originalWebTestClient.get().uri("/api/v1/games")
                .exchange()
                .expectStatus().isOk();

        /*
        TODO fix REST docs
        1. Currently I have NPE here: org.springframework.restdocs.ManualRestDocumentation.beforeOperation
        */

        newTestClient.get().uri("/api/v1/games")
                .exchange()
                .expectStatus().isOk();
    }

}
