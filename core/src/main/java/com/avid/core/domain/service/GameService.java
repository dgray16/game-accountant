package com.avid.core.domain.service;

import com.avid.core.domain.model.entity.Game;
import com.avid.core.domain.repository.GameRepository;
import com.avid.core.domain.service.base.DefaultCrudSupport;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameService extends DefaultCrudSupport<Game> {

    GameRepository gameRepository;

    public GameService(GameRepository repository) {
        super(repository);
        this.gameRepository = repository;
    }

}