package com.avid.web.player.v1.service;

import com.avid.core.domain.service.PlayerService;
import com.avid.web.player.v1.model.PlayerDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerWebService {

    PlayerService playerService;

    ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<PlayerDTO> getPlayers() {
        return playerService.findAll().stream()
                .map(player -> modelMapper.map(player, PlayerDTO.class))
                .collect(Collectors.toList());
    }

}
