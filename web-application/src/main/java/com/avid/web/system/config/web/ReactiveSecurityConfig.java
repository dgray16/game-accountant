package com.avid.web.system.config.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Slf4j
@AllArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReactiveSecurityConfig {

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                    .pathMatchers("/docs/**", "/api/**").permitAll();

        return http.build();
    }

    /**
     * @see <a href="https://docs.spring.io/spring-security/site/docs/5.0.8.RELEASE/reference/htmlsingle/#pe-dpe">Delegating Password Encoder</a>
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
