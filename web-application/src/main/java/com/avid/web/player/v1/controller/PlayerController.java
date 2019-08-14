package com.avid.web.player.v1.controller;

import com.avid.web.system.functional.FunctionalController;
import com.avid.web.system.functional.ServerRequestHelper;
import com.avid.web.player.v1.model.dto.PlayerDto;
import com.avid.web.player.v1.service.PlayerWebService;
import com.avid.web.system.security.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@ResponseBody
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlayerController implements FunctionalController {

    PlayerWebService playerWebService;
    PermissionService permissionService;

    @Override
    public RouterFunction<ServerResponse> defineRouter(Supplier<ServerRequestHelper> requestConverter) {
        return RouterFunctions
                .route()
                .nest(
                        RequestPredicates.path("/api/v1"),
                        builder -> builder
                                .GET("/players", request -> getPlayers()).filter(permissionService::canGetPlayers)
                                .GET("/players/{id}", this::getPlayer)
                )
                .build();
    }

    private Mono<ServerResponse> getPlayers() {
        return ServerResponse.ok().body(playerWebService.getPlayers(), PlayerDto.class);
    }

    private Mono<ServerResponse> getPlayer(ServerRequest request) {
        ObjectId playerId = new ObjectId(request.pathVariable("id"));
        return ServerResponse.ok().body(playerWebService.getPlayer(playerId), PlayerDto.class);
    }

}
