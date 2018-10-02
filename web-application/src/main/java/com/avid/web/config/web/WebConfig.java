package com.avid.web.config.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableWebFlux
@Configuration
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebConfig implements WebFluxConfigurer {

    CorsConfig corsConfig;

    @Override
    public void addCorsMappings(org.springframework.web.reactive.config.CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins(corsConfig.getHttpOrigins())
                .allowedMethods(corsConfig.getHttpMethods());
    }

    /**
     * Configures primary {@link ObjectMapper} that will be used in HTTP Server.
     */
    @Bean
    @Primary
    ObjectMapper objectMapper() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

        /* If value is null, we do not include it in JSON */
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);

        /* Write dates in Date ISO format */
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return builder.build();
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
