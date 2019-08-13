package com.avid.web.system.config.web;

import com.avid.core.domain.service.GameService;
import com.avid.core.domain.service.PlayerService;
import com.avid.web.system.functional.ServerRequestHelper;
import com.avid.web.game.v1.controller.GameController;
import com.avid.web.game.v1.service.GameWebService;
import com.avid.web.player.v1.controller.PlayerController;
import com.avid.web.player.v1.service.PlayerWebService;
import com.avid.web.solr.service.SolrGameService;
import com.avid.web.system.security.PermissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.reactive.function.server.RouterFunction;

import javax.validation.Validation;
import java.util.function.Supplier;

/**
 * After {@link GenericApplicationContext#refresh()}
 * I want to register {@link org.springframework.context.annotation.Bean}s related to my application.
 */
public class ApplicationBeanInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

    @Override
    public void initialize(GenericApplicationContext applicationContext) {
        registerDatabaseServices(applicationContext);
        registerWebServices(applicationContext);
        registerControllers(applicationContext);
        //registerSolrServices(applicationContext);
        registerAdditionalServices(applicationContext);
    }

    private void registerControllers(GenericApplicationContext context) {
        context.registerBean(
                ServerRequestHelper.class,
                () -> new ServerRequestHelper(
                        context.getBean(ObjectMapper.class), Validation.buildDefaultValidatorFactory().getValidator()
                ),
                customizer -> customizer.setLazyInit(true)
        );

        registerLazyBean(context, GameController.class);
        registerLazyBean(context, PlayerController.class);

        context.registerBean(
                RouterFunction.class,
                () -> {
                    Supplier<ServerRequestHelper> converter = () -> context.getBean(ServerRequestHelper.class);

                    return context.getBean(GameController.class).defineRouter(converter)
                            .and(context.getBean(PlayerController.class).defineRouter(converter));
                },
                customizer -> customizer.setLazyInit(true)
        );
    }

    private void registerDatabaseServices(GenericApplicationContext context) {
        registerLazyBean(context, PlayerService.class);
        registerLazyBean(context, GameService.class);
    }

    /*private void registerSolrServices(GenericApplicationContext context) {
        registerLazyBean(context, SolrGameService.class);
    }*/

    private void registerWebServices(GenericApplicationContext context) {
        registerLazyBean(context, GameWebService.class);
        registerLazyBean(context, PlayerWebService.class);
    }

    private void registerAdditionalServices(GenericApplicationContext context) {
        registerLazyBean(context, PermissionService.class);
    }

    private void registerLazyBean(GenericApplicationContext context, Class<?> classType) {
        context.registerBean(classType, customizer -> customizer.setLazyInit(true));
    }

}
