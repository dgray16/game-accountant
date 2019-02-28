package com.avid.web.config.web;

import com.avid.web.system.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Global exception handler.
 *
 * @see <a href="https://www.baeldung.com/spring-webflux-errors">Handling Errors</a>
 */
@Slf4j
@Component
@Order(-2)
@Profile("!local-test-documentation")
public class RestExceptionHandler extends AbstractErrorWebExceptionHandler {

    public RestExceptionHandler(ErrorAttributes errorAttributes,
                                ApplicationContext applicationContext,
                                ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, new ResourceProperties(), applicationContext);
        super.setMessageReaders(serverCodecConfigurer.getReaders());
        super.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::handleError);
    }

    private Mono<ServerResponse> handleError(ServerRequest serverRequest) {
        Throwable error = getError(serverRequest);

        Mono<ServerResponse> result;

        if (error instanceof UnsupportedOperationException) {
            result = badRequest(error);
        } else if (error instanceof BadRequestException) {
            result = badRequest(error);
        } else if (error instanceof SecurityException) {
            result = forbidden(error);
        } else {
            result = internalServerError(error);
        }

        return result;
    }

    private Mono<ServerResponse> badRequest(Throwable error) {
        return ServerResponse
                .badRequest()
                .body(Mono.just(error.getMessage()), String.class);
    }

    private Mono<ServerResponse> forbidden(Throwable error) {
        return ServerResponse
                .status(HttpStatus.FORBIDDEN)
                .syncBody(error.getMessage());
    }

    private Mono<ServerResponse> internalServerError(Throwable error) {
        log.error("Server error", error);

        return ServerResponse
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

}
