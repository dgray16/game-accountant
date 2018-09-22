package com.avid.web.game.v1.service;

import com.avid.core.domain.model.entity.Game;
import com.avid.core.domain.service.GameService;
import com.avid.web.game.v1.model.GameDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GamesWebService {

    GameService gameService;

    ModelMapper modelMapper;

    public Flux<GameDTO> getGames() {
        return gameService.findAll()
                .map(game -> modelMapper.map(game, GameDTO.class));
    }

}
