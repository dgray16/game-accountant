package com.avid.web.game.v1.controller;

import com.avid.web.game.v1.model.GameDTO;
import com.avid.web.game.v1.service.GamesWebService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameController {

    GamesWebService gamesWebService;

    public Flux<GameDTO> getGames() {
        return gamesWebService.getGames();
    }

}
