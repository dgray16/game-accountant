package com.avid.web.config.web;

import com.avid.core.domain.model.dictionary.GameGenre;
import com.avid.core.domain.model.entity.Game;
import com.avid.core.domain.model.entity.Player;
import com.avid.core.domain.service.GameService;
import com.avid.core.domain.service.PlayerService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationInitializator implements ApplicationListener<ApplicationReadyEvent> {

    PlayerService playerService;
    GameService gameService;

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
           if (BooleanUtils.isFalse(value)) {
               Game game = new Game();
               game.setName("Divinity: Original Sin 2");
               game.setGenres(List.of(GameGenre.ADVENTURE));
               gameService.create(game).subscribe();
           }
        });
    }
}
