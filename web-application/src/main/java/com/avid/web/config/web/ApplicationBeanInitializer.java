package com.avid.web.config.web;

import com.avid.core.domain.service.GameService;
import com.avid.core.domain.service.PlayerService;
import com.avid.web.functional.ServerRequestHelper;
import com.avid.web.game.v1.controller.GameController;
import com.avid.web.game.v1.service.GameWebService;
import com.avid.web.player.v1.controller.PlayerController;
import com.avid.web.player.v1.service.PlayerWebService;
import com.avid.web.solr.service.SolrGameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.reactive.function.server.RouterFunction;

import javax.validation.Validation;
import java.util.function.Supplier;

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
        context.registerBean(
                ServerRequestHelper.class,
                () -> new ServerRequestHelper(
                        context.getBean(ObjectMapper.class), Validation.buildDefaultValidatorFactory().getValidator()
                )
        );

        context.registerBean(GameController.class);
        context.registerBean(PlayerController.class);

        context.registerBean(
                RouterFunction.class,
                () -> {
                    Supplier<ServerRequestHelper> converter = () -> context.getBean(ServerRequestHelper.class);

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
