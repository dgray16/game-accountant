package com.avid.web.config.web;

import com.avid.core.domain.repository.GameRepository;
import com.avid.core.domain.repository.PlayerRepository;
import com.avid.core.domain.service.GameService;
import com.avid.core.domain.service.PlayerService;
import com.avid.web.game.v1.service.GameWebService;
import com.avid.web.player.v1.service.PlayerWebService;
import com.avid.web.solr.repository.SolrGameRepository;
import com.avid.web.solr.service.SolrGameService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.solr.client.solrj.SolrClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;

import java.util.Arrays;
import java.util.Optional;

/**
 * After {@link GenericApplicationContext#refresh()}
 * I want to register {@link org.springframework.context.annotation.Bean}s related to my application.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationBeanInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        GenericApplicationContext context = (GenericApplicationContext) event.getApplicationContext();
        var currentProfile = Arrays.stream(context.getEnvironment().getActiveProfiles()).findFirst();

        registerDatabaseServices(context);
        registerWebServices(context);
        registerSolrServices(context);
        registerSpringListeners(context, currentProfile);
    }

    private void registerSpringListeners(GenericApplicationContext context, Optional<String> currentProfile) {
        currentProfile
                .filter(profile -> "local".equals(profile) || "stage".equals(profile))
                .ifPresent(profile -> context.addApplicationListener(new ApplicationInitializer(
                        context.getBean(PlayerService.class),
                        context.getBean(GameService.class),
                        context.getBean(SolrGameService.class)
                )));
    }

    private void registerDatabaseServices(GenericApplicationContext context) {
        context.registerBean(PlayerService.class, () -> new PlayerService(context.getBean(PlayerRepository.class)));
        context.registerBean(GameService.class, () -> new GameService(context.getBean(GameRepository.class)));
    }

    private void registerSolrServices(GenericApplicationContext context) {
        context.registerBean(SolrConfig.class);
        SolrConfig solrBean = context.getBean(SolrConfig.class);
        context.registerBean(SolrClient.class, solrBean::solrClient);
        context.registerBean(SolrTemplate.class, () -> solrBean.solrTemplate(context.getBean(SolrClient.class)));

        context.registerBean(SolrGameService.class, () -> new SolrGameService(context.getBean(SolrGameRepository.class)));
    }

    private void registerWebServices(GenericApplicationContext context) {
        context.registerBean(
                GameWebService.class,
                () -> new GameWebService(context.getBean(GameService.class), context.getBean(SolrGameService.class))
        );

        context.registerBean(PlayerWebService.class, () -> new PlayerWebService(context.getBean(PlayerService.class)));
    }

}
