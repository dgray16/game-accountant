package com.avid.web.documentation;

import com.avid.core.domain.model.dictionary.GameGenre;
import com.avid.core.domain.model.entity.Game;
import com.avid.core.domain.service.GameService;
import com.avid.web.base.EmbeddedMongoDocumentationTest;
import com.avid.web.config.web.HATEOASRelationship;
import com.avid.web.game.v1.model.GameDTO;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.hypermedia.HypermediaDocumentation;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;

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
        gameService.create(game).block();

        getWebTestClient().get().uri("/api/v1/games")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GameDTO.class)
                .consumeWith(
                        WebTestClientRestDocumentation.document(generateDocumentName("get-games"),
                                HypermediaDocumentation.links(
                                        getGameLinkExtractor(),
                                        HypermediaDocumentation.linkWithRel(HATEOASRelationship.SELF.getValue()).description("Get this game"),
                                        HypermediaDocumentation.linkWithRel(HATEOASRelationship.DELETE.getValue()).description("Delete this game")
                                ),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("[].name").description("Name of the Game"),
                                        PayloadDocumentation.fieldWithPath("[].gameGenres").description(generateEnumValuesDescription(GameGenre.values())),
                                        PayloadDocumentation.subsectionWithPath("[].links").description(LINKS_DESCRIPTION)
                                )
                        )
                );
    }

    @Override
    protected String getControllerName() {
        return "game-controller";
    }
}
