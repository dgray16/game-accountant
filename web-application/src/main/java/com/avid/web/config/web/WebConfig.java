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
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebConfig implements WebMvcConfigurer {

    CorsConfig corsConfig;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins(corsConfig.getHttpOrigins())
                .allowedMethods(corsConfig.getHttpMethods());
    }

    /**
     * {@inheritDoc}
     *
     * Configure and build {@link com.fasterxml.jackson.databind.ObjectMapper} for HTTP web server.
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper()));
    }

    /**
     * Configures primary {@link ObjectMapper} that will be used in HTTP Server and WebSocket Server.
     *
     * @see WebConfig#configureMessageConverters(List)
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
