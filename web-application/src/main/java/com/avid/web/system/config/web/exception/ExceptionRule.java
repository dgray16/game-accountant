package com.avid.web.system.config.web.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
final class ExceptionRule {

    Class exceptionClass;
    Function<Throwable, Mono<ServerResponse>> function;

    private boolean supports(Throwable error) {
        return exceptionClass.isInstance(error);
    }

    Mono<ServerResponse> getResponse(Throwable error) {
        return supports(error) ? function.apply(error) : null;
    }
}
