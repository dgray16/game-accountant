package com.avid.web.documentation;

import com.avid.core.domain.model.entity.Player;
import com.avid.core.domain.service.PlayerService;
import com.avid.web.base.EmbeddedMongoDocumentationTest;
import com.avid.web.player.v1.model.PlayerDTO;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;
import reactor.test.StepVerifier;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerControllerDocumentationTest extends EmbeddedMongoDocumentationTest {

    @Autowired
    PlayerService playerService;

    @Test
    public void testGetPlayers() {
        Player player = new Player();
        player.setEmail("vova@player.com");

        StepVerifier
                .create(playerService.create(player))
                .expectSubscription()
                .then(() -> getWebTestClient().get().uri("/api/v1/players")
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(PlayerDTO.class)
                        .consumeWith(
                                WebTestClientRestDocumentation.document(
                                        generateDocumentName("get-players"),
                                        PayloadDocumentation.responseFields(
                                                PayloadDocumentation.fieldWithPath("[].email").description("Email of Player")
                                        )
                                )
                        )
        );
    }

    @Override
    protected String getControllerName() {
        return "player-controller";
    }

}
