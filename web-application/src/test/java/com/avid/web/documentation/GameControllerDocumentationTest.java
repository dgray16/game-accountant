package com.avid.web.documentation;

import com.avid.core.domain.model.dictionary.GameGenre;
import com.avid.core.domain.model.entity.Game;
import com.avid.core.domain.service.GameService;
import com.avid.web.base.EmbeddedMongoDocumentationTest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.hypermedia.HypermediaDocumentation;
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
        gameService.create(game).subscribe();

        getWebTestClient().get().uri("/api/v1/games")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("[0].name").isEqualTo(game.getName())
                .consumeWith(
                        WebTestClientRestDocumentation.document("get-games",
                                HypermediaDocumentation.links(
                                        getGameLinkExtractor(),
                                        HypermediaDocumentation.linkWithRel("self").description("Get this game"),
                                        HypermediaDocumentation.linkWithRel("delete").description("Delete this game")
                                )
                        ));
    }

}
