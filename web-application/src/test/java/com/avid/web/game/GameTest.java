package com.avid.web.game;

import com.avid.core.domain.model.dictionary.GameGenre;
import com.avid.core.domain.model.entity.Game;
import com.avid.core.domain.service.GameService;
import com.avid.web.base.EmbeddedMongoTest;
import com.avid.web.game.v1.controller.GameController;
import com.avid.web.game.v1.model.dto.GameDTO;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

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

        GameDTO expectedResponse = new GameDTO();
        expectedResponse.setGameGenres(game.getGenres());
        expectedResponse.setName(game.getName());

        getWebTestClient().get().uri("/api/v1/games")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GameDTO.class)
                .hasSize(NumberUtils.INTEGER_ONE)
                .contains(expectedResponse);
    }

}
