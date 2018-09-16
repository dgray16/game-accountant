package com.avid.core.domain.service;

import com.avid.core.domain.model.entity.Player;
import com.avid.core.domain.repository.PlayerRepository;
import com.avid.core.domain.service.base.DefaultCrudSupport;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerService extends DefaultCrudSupport<Player> {

    PlayerRepository playerRepository;

    public PlayerService(PlayerRepository repository) {
        super(repository);
        this.playerRepository = repository;
    }

}
