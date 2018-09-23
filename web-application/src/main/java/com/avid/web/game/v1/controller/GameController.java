package com.avid.web.game.v1.controller;

import com.avid.web.game.v1.model.GameDTO;
import com.avid.web.game.v1.service.GameWebService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameController {

    GameWebService gameWebService;

    @GetMapping(value = "/api/v1/games")
    public Flux<GameDTO> getGames() {
        return gameWebService.getGames();
    }

    @GetMapping(value = "/api/v1/game/{id}")
    public Mono<GameDTO> getGame(@PathVariable ObjectId id) {
        return gameWebService.getGame(id);
    }

    @DeleteMapping(value = "/api/v1/game/{id}")
    public Mono<ResponseEntity> deleteGame(@PathVariable ObjectId id) {
        gameWebService.delete(id);
        return Mono.just(ResponseEntity.noContent().build());
    }

}
