package com.avid.web.functional;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.function.Supplier;

@FunctionalInterface
public interface FunctionalController {

    /**
     * Convenient way to setup mappings for specific {@link org.springframework.stereotype.Controller}.
     */
    RouterFunction<ServerResponse> defineRouter(Supplier<ServerRequestHelper> requestConverter);

}
