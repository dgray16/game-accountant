package com.avid.web.config.web;

import com.avid.core.domain.service.GameService;
import com.avid.core.domain.service.PlayerService;
import com.avid.web.functional.ServerRequestConverter;
import com.avid.web.game.v1.controller.GameController;
import com.avid.web.game.v1.service.GameWebService;
import com.avid.web.player.v1.service.PlayerWebService;
import com.avid.web.solr.service.SolrGameService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.reactive.function.server.RouterFunction;

/**
 * After {@link GenericApplicationContext#refresh()}
 * I want to register {@link org.springframework.context.annotation.Bean}s related to my application.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationBeanInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

    @Override
    public void initialize(GenericApplicationContext applicationContext) {
        registerDatabaseServices(applicationContext);
        registerWebServices(applicationContext);
        registerControllers(applicationContext);
        registerSolrServices(applicationContext);
    }

    private void registerControllers(GenericApplicationContext context) {
        context.registerBean(ServerRequestConverter.class);

        context.registerBean(GameController.class);
        context.registerBean(
                RouterFunction.class,
                () -> context
                        .getBean(GameController.class)
                        .defineRouter(() -> context.getBean(ServerRequestConverter.class))
        );
    }

    private void registerDatabaseServices(GenericApplicationContext context) {
        context.registerBean(PlayerService.class);
        context.registerBean(GameService.class);
    }

    private void registerSolrServices(GenericApplicationContext context) {
        context.registerBean(SolrGameService.class);
    }

    private void registerWebServices(GenericApplicationContext context) {
        context.registerBean(GameWebService.class);
        context.registerBean(PlayerWebService.class);
    }


}
