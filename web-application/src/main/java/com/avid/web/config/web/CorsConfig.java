package com.avid.web.config.web;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Setter
@Configuration
@ConfigurationProperties(value = "game.cors.http")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CorsConfig {

    List<String> allowedOrigins = new ArrayList<>();
    List<String> allowedMethods = new ArrayList<>();


    String[] getHttpOrigins() {
        return allowedOrigins.toArray(new String[NumberUtils.INTEGER_ZERO]);
    }

    String[] getHttpMethods() {
        return allowedMethods.toArray(new String[NumberUtils.INTEGER_ZERO]);
    }
}
