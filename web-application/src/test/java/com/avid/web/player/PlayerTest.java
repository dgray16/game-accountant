package com.avid.web.player;

import com.avid.core.domain.model.entity.Player;
import com.avid.core.domain.service.PlayerService;
import com.avid.web.base.EmbeddedMongoTest;
import com.avid.web.player.v1.model.PlayerDTO;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerTest extends EmbeddedMongoTest {

    @Autowired
    PlayerService playerService;

    @Test
    public void testGetPlayers() {
        playerService.create(player -> player.setEmail("vova@test.com")).subscribe();
        playerService.create(player -> player.setEmail("antony@test.com")).subscribe();

        List<Player> createdPlayers = playerService.findAll().toStream()
                .collect(Collectors.toList());

        List<PlayerDTO> expectedResponse = createdPlayers.stream()
                .map(PlayerDTO::of)
                .collect(Collectors.toList());

        getWebTestClient().get().uri("/api/v1/players")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PlayerDTO.class)
                .hasSize(NumberUtils.INTEGER_TWO)
                .isEqualTo(expectedResponse);
    }

    @Test
    public void testGetPlayer() {
        playerService
                .create(player -> player.setEmail("dunkan@dragon.age"))
                .subscribe(createdPlayer -> {
                    PlayerDTO expectedResponse = PlayerDTO.of(createdPlayer);

                    getWebTestClient().get().uri("/api/v1/players/{0}", createdPlayer.getId().toHexString())
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(PlayerDTO.class)
                            .isEqualTo(expectedResponse);
                });
    }

}
