package com.avid.web.player.v1.controller;

import com.avid.web.functional.FunctionalController;
import com.avid.web.functional.ServerRequestConverter;
import com.avid.web.player.v1.model.PlayerDTO;
import com.avid.web.player.v1.service.PlayerWebService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@ResponseBody
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerController implements FunctionalController {

    PlayerWebService playerWebService;

    @Override
    public RouterFunction<ServerResponse> defineRouter(Supplier<ServerRequestConverter> requestConverter) {
        return RouterFunctions
                .route()
                .path("/api/v1/players", builder -> builder.GET("", request -> getPlayers()))
                .build();
    }

    private Mono<ServerResponse> getPlayers() {
        return ServerResponse.ok().body(playerWebService.getPlayers(), PlayerDTO.class);
    }


}
