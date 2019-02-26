package com.avid.web;

import com.avid.web.config.web.ApplicationBeanInitializer;
import com.avid.web.config.web.AksoCorsConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * TODO
 * 1. Micrometer / Actuator / Grafana
 * 2. OAuth2 Authorization / WebAuthn
 * 3. Logstash / Sentry
 * 4. Remove default user org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration#getOrDeducePassword(org.springframework.boot.autoconfigure.security.SecurityProperties.User, org.springframework.security.crypto.password.PasswordEncoder)
 * 5. Jigsaw? ( https://www.youtube.com/watch?v=wX0zhalTgzQ -- https://github.com/rzwitserloot/lombok/issues/1723 )
 * 7. Implement Solr on Heroku
 * 8. Fix documentation on Heroku
 */
@SpringBootApplication
@EnableConfigurationProperties(value = {
        AksoCorsConfig.class
})
@EnableReactiveMongoRepositories(basePackages = "com.avid.core.domain.repository")
public class GameaccountantApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(GameaccountantApplication.class)
                .initializers(new ApplicationBeanInitializer())
                .run(args);
    }
}
