package com.avid.web.player.v1.model.mapper;

import com.avid.core.domain.model.entity.Player;
import com.avid.web.player.v1.model.dto.PlayerDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlayerDtoMapper {

    PlayerDtoMapper INSTANCE = Mappers.getMapper(PlayerDtoMapper.class);

    PlayerDto map(Player player);

}
