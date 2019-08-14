package com.avid.web.player;

import com.avid.core.domain.model.entity.Player;
import com.avid.core.domain.service.PlayerService;
import com.avid.web.base.EmbeddedMongoTest;
import com.avid.web.player.v1.model.dto.PlayerDto;
import com.avid.web.player.v1.model.mapper.PlayerDtoMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerTest extends EmbeddedMongoTest {

    @Autowired
    PlayerService playerService;

    @Test
    public void testGetPlayers() {
        Mono<Player> firstPlayer = playerService.create(player -> player.setEmail("vova@test.com"));
        Mono<Player> secondPlayer = playerService.create(player -> player.setEmail("antony@test.com"));

        Flux
                .concat(firstPlayer, secondPlayer)
                .subscribe(tuple -> {
                    List<Player> createdPlayers = playerService.findAll().toStream()
                            .collect(Collectors.toList());

                    List<PlayerDto> expectedResponse = createdPlayers.stream()
                            .map(PlayerDtoMapper.INSTANCE::map)
                            .collect(Collectors.toList());

                    getWebTestClient().get().uri("/api/v1/players")
                            .exchange()
                            .expectStatus().isOk()
                            .expectBodyList(PlayerDto.class)
                            .hasSize(NumberUtils.INTEGER_TWO)
                            .isEqualTo(expectedResponse);
                });
    }

    @Test
    public void testGetPlayer() {
        playerService
                .create(player -> player.setEmail("dunkan@dragon.age"))
                .subscribe(createdPlayer -> {
                    PlayerDto expectedResponse = PlayerDtoMapper.INSTANCE.map(createdPlayer);

                    getWebTestClient().get().uri("/api/v1/players/{0}", createdPlayer.getId().toHexString())
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(PlayerDto.class)
                            .isEqualTo(expectedResponse);
                });
    }

}
