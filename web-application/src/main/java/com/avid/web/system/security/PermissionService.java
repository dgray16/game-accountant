package com.avid.web.system.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Alternative to {@link org.springframework.security.access.prepost.PreAuthorize}.
 */
public class PermissionService {

    public Mono<ServerResponse> canGetPlayers(ServerRequest request, HandlerFunction<ServerResponse> next) {
        return next.handle(request);
    }

    private static Mono<ServerResponse> forbidden() {
        return ServerResponse.status(HttpStatus.FORBIDDEN).build();
    }

}
