package com.avid.web.game.v1.model.mapper;

import com.avid.core.domain.model.entity.Game;
import com.avid.web.game.v1.model.dto.GameDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GameDtoMapper {

    GameDtoMapper INSTANCE = Mappers.getMapper(GameDtoMapper.class);

    @Mapping(source = "genres", target = "gameGenres")
    GameDto map(Game game);

}
