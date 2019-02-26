package com.avid.web.player.v1.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerController {

    /*PlayerWebService playerWebService;

    @GetMapping(value = "/api/v1/players")
    public Flux<PlayerDTO> getPlayers() {
        return playerWebService.getPlayers();
    }*/

}
