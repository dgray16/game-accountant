package com.avid.web.player.v1.service;

import com.avid.core.domain.service.PlayerService;
import com.avid.web.player.v1.model.PlayerDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerWebService {

    PlayerService playerService;

    ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Flux<PlayerDTO> getPlayers() {
        return playerService.findAll()
                .map(player -> modelMapper.map(player, PlayerDTO.class));
    }

}
