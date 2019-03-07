package com.avid.web.system.config.web.exception;

import com.avid.web.system.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * Global exception handler.
 *
 * {@link AbstractErrorWebExceptionHandler} used only for this method:
 * #{@link AbstractErrorWebExceptionHandler#getError(ServerRequest)}.
 */
@Slf4j
@RestControllerAdvice
@Profile("!local-test-documentation")
public class RestExceptionHandler extends AbstractErrorWebExceptionHandler {

    private static final List<ExceptionRule> EXCEPTION_RULES = List.of(
            new ExceptionRule(UnsupportedOperationException.class, RestExceptionHandler::badRequest),
            new ExceptionRule(BadRequestException.class, RestExceptionHandler::badRequest),
            new ExceptionRule(SecurityException.class, RestExceptionHandler::forbidden)
    );

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

        return EXCEPTION_RULES.stream()
                .map(exceptionRule -> exceptionRule.getResponse(error))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseGet(() -> internalServerError(error));
    }

    private static Mono<ServerResponse> badRequest(Throwable error) {
        return ServerResponse
                .badRequest()
                .body(Mono.just(error.getMessage()), String.class);
    }

    private static Mono<ServerResponse> forbidden(Throwable error) {
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
