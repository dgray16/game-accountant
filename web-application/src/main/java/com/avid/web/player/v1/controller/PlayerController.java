package com.avid.web.player.v1.controller;

import com.avid.web.player.v1.model.PlayerDTO;
import com.avid.web.player.v1.service.PlayerWebService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerController {

    PlayerWebService playerWebService;

    @GetMapping(value = "/test")
    public Flux<PlayerDTO> getPlayers() {
        return playerWebService.getPlayers();
    }

}
