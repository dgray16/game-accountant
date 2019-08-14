package com.avid.web.game;

import com.avid.core.domain.model.dictionary.GameGenre;
import com.avid.core.domain.model.entity.Game;
import com.avid.core.domain.service.GameService;
import com.avid.web.base.EmbeddedMongoTest;
import com.avid.web.game.v1.controller.GameController;
import com.avid.web.game.v1.model.dto.GameDto;
import com.avid.web.game.v1.model.mapper.GameDtoMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.util.List;

/**
 * Test class for {@link GameController}.
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

        StepVerifier
                .create(gameService.create(game).log("Game created"))
                .expectSubscription()
                .then(() -> {
                    GameDto expectedResponse = new GameDto();
                    expectedResponse.setGameGenres(game.getGenres());
                    expectedResponse.setName(game.getName());

                    getWebTestClient().get().uri("/api/v1/games")
                            .exchange()
                            .expectStatus().isOk()
                            .expectBodyList(GameDto.class)
                            .hasSize(NumberUtils.INTEGER_ONE)
                            .isEqualTo(List.of(expectedResponse));
                });
    }

    @Test
    public void testGetGame() {
        gameService
                .create(game -> {
                    game.setName("Resident Evil 2");
                    game.setGenres(List.of(GameGenre.SURVIVAL));
                })
                .subscribe(createdGame -> {
                    GameDto expectedResponse = GameDtoMapper.INSTANCE.map(createdGame);

                    getWebTestClient().get().uri("/api/v1/games/{0}", createdGame.getId().toHexString())
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(GameDto.class)
                            .isEqualTo(expectedResponse);
                });
    }

}
