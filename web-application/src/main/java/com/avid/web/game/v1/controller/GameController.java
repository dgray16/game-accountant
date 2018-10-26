package com.avid.web.game.v1.controller;

import com.avid.web.game.v1.model.GameDTO;
import com.avid.web.game.v1.service.GameWebService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
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
    public ResponseEntity<Flux<GameDTO>> getGames() {
        return ResponseEntity.ok(gameWebService.getGames());
    }

    /**
     * curl -v -H "Accept:application/stream+json" http://localhost:8080/api/v1/games
     */
    @GetMapping(value = "/api/v1/games", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public ResponseEntity<Flux<GameDTO>> getGamesStream() {
        return ResponseEntity.ok(gameWebService.getGames());
    }

    @GetMapping(value = "/api/v1/game/{id}")
    public Mono<ResponseEntity<GameDTO>> getGame(@PathVariable ObjectId id) {
        return gameWebService.getGame(id)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping(value = "/api/v1/game/{id}")
    public Mono<ResponseEntity<Void>> deleteGame(@PathVariable ObjectId id) {
        return gameWebService.delete(id)
                .map(obj -> ResponseEntity.noContent().build());
    }

}
