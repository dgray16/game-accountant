package com.avid.web.config.web;

import com.avid.core.domain.model.entity.Player;
import com.avid.core.domain.service.PlayerService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationInitializator implements ApplicationListener<ApplicationReadyEvent> {

    PlayerService playerService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
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
}
