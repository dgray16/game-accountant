package com.avid.web.config.web;

import com.avid.core.domain.model.entity.Player;
import com.avid.core.domain.service.PlayerService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;


@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationInitializator implements ApplicationListener {

    PlayerService playerService;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        List<Player> players = playerService.findAll();

        if (players.isEmpty()) {
            Player player = new Player();
            player.setEmail("vova@player.com");
            playerService.create(player);

            Player player1 = new Player();
            player1.setEmail("vova1@player.com");
            playerService.create(player1);
        }
    }
}
