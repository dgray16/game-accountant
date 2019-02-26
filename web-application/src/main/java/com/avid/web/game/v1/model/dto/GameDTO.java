package com.avid.web.game.v1.model.dto;

import com.avid.core.domain.model.dictionary.GameGenre;
import com.avid.core.domain.model.entity.Game;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameDTO {

    String name;
    List<GameGenre> gameGenres;

    public static GameDTO of(Game game) {
        GameDTO dto = new GameDTO();

        dto.setName(game.getName());
        dto.setGameGenres(game.getGenres());

        return dto;
    }

}
