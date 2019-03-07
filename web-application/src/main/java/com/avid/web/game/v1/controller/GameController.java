package com.avid.web.game.v1.controller;

import com.avid.web.system.functional.FunctionalController;
import com.avid.web.system.functional.ServerRequestHelper;
import com.avid.web.game.v1.model.dto.GameDTO;
import com.avid.web.game.v1.model.request.GetGamesRequest;
import com.avid.web.game.v1.service.GameWebService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Slf4j
@ResponseBody
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameController implements FunctionalController {

    GameWebService gameWebService;

    @Override
    public RouterFunction<ServerResponse> defineRouter(Supplier<ServerRequestHelper> supplier) {
        ServerRequestHelper converter = supplier.get();
        return RouterFunctions
                .route()
                .nest(
                        RequestPredicates.path("/api/v1"),
                        builder -> builder
                                .GET(
                                        "/games",
                                        request -> getGames(converter.mapQueryParamsWithValidation(request, GetGamesRequest.class))
                                )
                                .GET("/games/{id}", this::getGame)
                )
                .build();
    }

    private Mono<ServerResponse> getGames(@Validated GetGamesRequest request) {
        return ServerResponse.ok().body(gameWebService.getGames(request), GameDTO.class);
    }

    private Mono<ServerResponse> getGame(ServerRequest request) {
        ObjectId id = new ObjectId(request.pathVariable("id"));
        return ServerResponse.ok().body(gameWebService.getGame(id), GameDTO.class);
    }

    /*@DeleteMapping(value = "/api/v1/game/{id}")*/
    public Mono<ResponseEntity<Void>> deleteGame(@PathVariable ObjectId id) {
        return gameWebService.delete(id)
                .map(obj -> ResponseEntity.noContent().build());
    }

}
