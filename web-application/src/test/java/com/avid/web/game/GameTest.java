package com.avid.web.game;

import com.avid.core.domain.model.dictionary.GameGenre;
import com.avid.core.domain.model.entity.Game;
import com.avid.core.domain.service.GameService;
import com.avid.web.game.v1.controller.GameController;
import com.avid.web.base.EmbeddedMongoTest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;

import java.util.List;

/**
 * TestClass for {@link GameController}.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameTest extends EmbeddedMongoTest {

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
                .consumeWith(WebTestClientRestDocumentation.document("test-smth"));
    }

}
