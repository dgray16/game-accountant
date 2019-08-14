package com.avid.core.domain.service;

import com.avid.core.domain.model.entity.Player;
import com.avid.core.domain.repository.PlayerRepository;
import com.avid.core.domain.service.base.DefaultCrudSupport;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerService extends DefaultCrudSupport<Player> {

    PlayerRepository playerRepository;

    public PlayerService(PlayerRepository repository) {
        super(repository);
        this.playerRepository = repository;
    }

    @Transactional(readOnly = true)
    public Mono<Player> findByEmail(String email) {
        return playerRepository.findFirstByEmail(email);
    }

}
