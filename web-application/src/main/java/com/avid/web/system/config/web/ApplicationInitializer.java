package com.avid.web.system.config.web;

import com.avid.core.domain.model.dictionary.GameGenre;
import com.avid.core.domain.model.entity.Game;
import com.avid.core.domain.model.entity.Player;
import com.avid.core.domain.service.GameService;
import com.avid.core.domain.service.PlayerService;
import com.avid.web.solr.model.SolrGame;
import com.avid.web.solr.service.SolrGameService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Generates data in Database.
 */
@Component
@AllArgsConstructor
@Profile(value = { "stage", "local" })
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationInitializer implements ApplicationListener<ApplicationReadyEvent> {

    PlayerService playerService;
    GameService gameService;

    //SolrGameService solrGameService; TODO enable in future

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        createPlayers();
        createGames();
    }

    private void createPlayers() {
        playerService.findAll().hasElements().subscribe(value -> {
            if (BooleanUtils.isFalse(value)) {
                Player player = new Player();
                player.setEmail("vova@player.com");
                playerService.create(player).subscribe();

                Player player1 = new Player();
                player1.setEmail("vova1@player.com");
                playerService.create(player1).subscribe();
            }
        });
    }

    private void createGames() {
        gameService.findAll().hasElements().subscribe(value -> {
            
            /*solrGameService.deleteData();*/

            if (BooleanUtils.isFalse(value)) {
                Game game1 = new Game();
                game1.setName("Divinity: Original Sin 2");
                game1.setGenres(List.of(GameGenre.ADVENTURE));

                gameService
                        .create(game1)
                        /*.map(SolrGame::of)*/
                        .subscribe(/*solrGameService::createIndex*/);

                Game game2 = new Game();
                game2.setName("Hitman: Absolution");
                game2.setGenres(List.of(GameGenre.STEALTH));

                gameService
                        .create(game2)
                        /*.map(SolrGame::of)*/
                        .subscribe(/*solrGameService::createIndex*/);
            }
        });
    }
}
