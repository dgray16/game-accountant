package com.avid.web.documentation;

import com.avid.core.domain.model.dictionary.GameGenre;
import com.avid.core.domain.model.entity.Game;
import com.avid.core.domain.service.GameService;
import com.avid.web.base.EmbeddedMongoDocumentationTest;
import com.avid.web.game.v1.model.dto.GameDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;
import reactor.test.StepVerifier;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameControllerDocumentationTest extends EmbeddedMongoDocumentationTest {

    @Autowired
    GameService gameService;

    @Test
    public void testGetGames() {
        Game game = new Game();
        game.setName("PUBG");
        game.setGenres(List.of(GameGenre.SHOOTER, GameGenre.SURVIVAL));

        StepVerifier
                .create(gameService.create(game))
                .expectSubscription()
                .then(() -> getWebTestClient().get().uri("/api/v1/games")
                        .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(GameDto.class)
                        .consumeWith(
                                WebTestClientRestDocumentation.document(
                                        generateDocumentName("get-games"),
                                        PayloadDocumentation.responseFields(
                                                PayloadDocumentation.fieldWithPath("[].name").description("Name of the Game"),
                                                PayloadDocumentation.fieldWithPath("[].gameGenres").description(generateEnumValuesDescription(GameGenre.values()))
                                        ),
                                        RequestDocumentation.requestParameters(
                                                RequestDocumentation.parameterWithName("query").description("Name of Game for search").optional()
                                        )
                                )
                        )
                );
    }

    @Override
    protected String getControllerName() {
        return "game-controller";
    }
}
