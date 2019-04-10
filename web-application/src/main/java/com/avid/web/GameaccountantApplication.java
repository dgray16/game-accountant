package com.avid.web;

import com.avid.web.system.config.web.ApplicationBeanInitializer;
import com.avid.web.system.config.web.AksoCorsConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * TODO
 * - Micrometer / Actuator / Grafana
 * - OAuth2 Authorization / WebAuthn
 * - Logstash / Sentry
 * - Remove default user org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration#getOrDeducePassword(org.springframework.boot.autoconfigure.security.SecurityProperties.User, org.springframework.security.crypto.password.PasswordEncoder)
 * - Jigsaw? ( https://www.youtube.com/watch?v=wX0zhalTgzQ -- https://github.com/rzwitserloot/lombok/issues/1723 )
 * - Implement Solr on Heroku
 * - Fix documentation on Heroku
 * - Programmatic transaction manager
 * - Pythia (https://github.com/VirgilSecurity/virgil-pythia-java)
 * - MapStruct ( http://mapstruct.org/ )
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
