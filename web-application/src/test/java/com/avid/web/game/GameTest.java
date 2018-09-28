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
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

/**
 * Test for {@link GameController}.
 */
// webTestClient filter - JunitRestDocumentation
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameTest extends EmbeddedMongoTest {

    @Autowired
    GameService gameService;

    /*@Autowired
    WebTestClient webTestClient;*/

    @Test
    public void testGetGames() {
        Game game = new Game();
        game.setName("PUBG");
        game.setGenres(List.of(GameGenre.SHOOTER, GameGenre.SURVIVAL));
        gameService.create(game).subscribe();

        /* TODO
         * 2. REST DOCS!
         */
        getWebTestClient().get().uri("/api/v1/games")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("[0].name").isEqualTo(game.getName());
    }

}
