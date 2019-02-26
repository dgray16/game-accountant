package com.avid.web.functional;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.function.Supplier;

@FunctionalInterface
public interface FunctionalController {

    RouterFunction<ServerResponse> defineRouter(Supplier<ServerRequestConverter> requestConverter);

}
