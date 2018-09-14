package com.avid.web.config.web;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServletServerHttpRequest;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CorsConfig {

    @Value("#{'${game.cors.http.allowedOrigins}'.split(',')}")
    List<String> allowedHttpOrigins = new ArrayList<>();

    @Value("#{'${game.cors.http.allowedMethods}'.split(',')}")
    List<String> allowedHttpMethods = new ArrayList<>();


    @PostConstruct
    void init() {
        allowedHttpOrigins = allowedHttpOrigins.stream()
                .map(String::trim)
                .collect(Collectors.toList());

        allowedHttpMethods = allowedHttpMethods.stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public Optional<String> getHttpOriginFromRequest(HttpServletRequest request) {
        String requestOrigin = new ServletServerHttpRequest(request).getHeaders().getOrigin();

        return Objects.nonNull(requestOrigin)
                ? allowedHttpOrigins.stream()
                .filter(requestOrigin::contains)
                .findFirst()
                : Optional.empty();
    }

    String[] getHttpOrigins() {
        return allowedHttpOrigins.toArray(new String[NumberUtils.INTEGER_ZERO]);
    }

    String[] getHttpMethods() {
        return allowedHttpMethods.toArray(new String[NumberUtils.INTEGER_ZERO]);
    }
}
