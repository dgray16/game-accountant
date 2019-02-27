package com.avid.web.base;

import com.avid.core.domain.service.GameService;
import com.avid.core.domain.service.PlayerService;
import com.avid.web.functional.ServerRequestConverter;
import com.avid.web.game.v1.controller.GameController;
import com.avid.web.game.v1.service.GameWebService;
import com.avid.web.player.v1.controller.PlayerController;
import com.avid.web.player.v1.service.PlayerWebService;
import com.avid.web.solr.service.SolrGameService;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.reactive.function.server.RouterFunction;

import java.util.function.Supplier;

public class TestBeanInitialize implements ApplicationContextInitializer<GenericApplicationContext> {

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
        context.registerBean(PlayerController.class);

        context.registerBean(
                RouterFunction.class,
                () -> {
                    Supplier<ServerRequestConverter> converter = () -> context.getBean(ServerRequestConverter.class);

                    /* TODO Maybe define /api/v1 ; /api/v2 here? */
                    return context.getBean(GameController.class).defineRouter(converter)
                            .and(context.getBean(PlayerController.class).defineRouter(converter));
                }
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
