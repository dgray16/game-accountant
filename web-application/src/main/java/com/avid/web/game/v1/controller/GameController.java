package com.avid.web.game.v1.controller;

import com.avid.web.functional.FunctionalController;
import com.avid.web.functional.ServerRequestConverter;
import com.avid.web.game.v1.model.dto.GameDTO;
import com.avid.web.game.v1.model.request.GetGamesRequest;
import com.avid.web.game.v1.service.GameWebService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Slf4j
@ResponseBody
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameController implements FunctionalController {

    GameWebService gameWebService;

    private Mono<ServerResponse> getGames(GetGamesRequest request) {
        return ServerResponse.ok().body(gameWebService.getGames(request), GameDTO.class);
    }

    /*@GetMapping(value = "/api/v1/game/{id}")*/
    public Mono<ResponseEntity<GameDTO>> getGame(@PathVariable ObjectId id) {
        return gameWebService.getGame(id)
                .map(ResponseEntity::ok);
    }

    /*@DeleteMapping(value = "/api/v1/game/{id}")*/
    public Mono<ResponseEntity<Void>> deleteGame(@PathVariable ObjectId id) {
        return gameWebService.delete(id)
                .map(obj -> ResponseEntity.noContent().build());
    }

    @Override
    public RouterFunction<ServerResponse> defineRouter(Supplier<ServerRequestConverter> supplier) {
        ServerRequestConverter converter = supplier.get();
        return RouterFunctions.route()
                .path(
                        "/api/v1/games",
                        builder -> builder.GET(
                                "",
                                request -> getGames(converter.mapQueryParams(request, GetGamesRequest.class)))
                )
                .build();
    }

    /**
     * curl -v -H "Accept:application/stream+json" http://localhost:8080/api/v1/games
     */
    /*@GetMapping(value = "/api/v1/games", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public ResponseEntity<Flux<GameDTO>> getGamesStream(@ModelAttribute GetGamesRequest request) {
        return ResponseEntity.ok(gameWebService.getGames(request));
    }*/

}
