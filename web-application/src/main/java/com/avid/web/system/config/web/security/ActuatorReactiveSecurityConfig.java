package com.avid.web.system.config.web.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * General steps:
 * 1. Install Prometeus
 * 2. Install Graphana
 * 3. Configure these services on local machine.
 *
 * @see <a href="https://www.callicoder.com/spring-boot-actuator-metrics-monitoring-dashboard-prometheus-grafana/">Grafana, Prometheus setup</a>
 * @see <a href="https://grafana.com/grafana/dashboards/4701">Grafana template</a>
 */
@Order(2)
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActuatorReactiveSecurityConfig {

    PasswordEncoder passwordEncoder;

    ReactiveAuthenticationManager actuatorAuthenticationManager() {
        UserDetails userDetails = User.builder()
                .username("actuator-local")
                .password(passwordEncoder.encode("secret"))
                .roles("ACTUATOR")
                .build();

        return new UserDetailsRepositoryReactiveAuthenticationManager(new MapReactiveUserDetailsService(userDetails));
    }

    @Bean
    SecurityWebFilterChain actuatorSecurityWebFilterChain(ServerHttpSecurity http) {
        http
                .securityMatcher(EndpointRequest.toAnyEndpoint())
                .authorizeExchange()
                .anyExchange()
                .hasRole("ACTUATOR")
                .and()
                .httpBasic()
                .authenticationManager(actuatorAuthenticationManager());

        return http.build();
    }

}
