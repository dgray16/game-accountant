package com.avid.web;

import com.avid.web.system.config.web.ApplicationBeanInitializer;
import com.avid.web.system.config.web.GameCorsConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * TODO
 * - OAuth2 Authorization / WebAuthn
 * - Logstash / Sentry
 * - Jigsaw ( https://www.youtube.com/watch?v=wX0zhalTgzQ -- https://www.youtube.com/watch?v=UFBH7gHJkb4 ) For some reason Intellij cannot compile modularized app (JDK12); codehaus container jar is not working.
 * - Implement Solr on Heroku
 * - Fix documentation on Heroku
 * - Pythia (https://github.com/VirgilSecurity/virgil-pythia-java)
 * - Bump up security ( https://github.com/ace0/Security101 )
 * - https://github.com/springdoc/springdoc-openapi
 */
@SpringBootApplication
@EnableConfigurationProperties(value = {
        GameCorsConfig.class
})
@EnableReactiveMongoRepositories(basePackages = "com.avid.core.domain.repository")
public class GameaccountantApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(GameaccountantApplication.class)
                .initializers(new ApplicationBeanInitializer())
                .run(args);
    }

}
