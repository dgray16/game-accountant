package com.avid.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * TODO
 * 1. Micrometer / Actuator / Grafana
 * 2. OAuth2 Authorization / WebAuthn
 * 3. Logstash / Sentry
 * 4. Remove default user org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration#getOrDeducePassword(org.springframework.boot.autoconfigure.security.SecurityProperties.User, org.springframework.security.crypto.password.PasswordEncoder)
 * 5. Jigsaw? ( https://www.youtube.com/watch?v=wX0zhalTgzQ )
 * 6. HATEOAS full hostname (Ready, check ResourceLinkBuilder)
 * 7. Implement Solr on Heroku
 * 8. Fix documentation on Heroku
 * 9. Implement RouterFunctions in functional style.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.avid")
@EnableReactiveMongoRepositories(basePackages = "com.avid.core.domain.repository")
public class GameaccountantApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameaccountantApplication.class, args);
    }

}
