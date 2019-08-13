package com.avid.web.player.v1.service;

import com.avid.core.domain.service.PlayerService;
import com.avid.web.player.v1.model.PlayerDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlayerWebService {

    PlayerService playerService;

    @Transactional(readOnly = true)
    public Flux<PlayerDTO> getPlayers() {
        return playerService.findAll().map(PlayerDTO::of);
    }

    @Transactional(readOnly = true)
    public Mono<PlayerDTO> getPlayer(ObjectId playerId) {
        return playerService.findById(playerId).map(PlayerDTO::of);
    }
}
