package com.avid.web.game.v1.model.dto;

import com.avid.core.domain.model.dictionary.GameGenre;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameDto {

    String name;
    List<GameGenre> gameGenres;

    /*public static GameDto of(Game game) {
        GameDto dto = new GameDto();

        dto.setName(game.getName());
        dto.setGameGenres(game.getGenres());

        return dto;
    }*/

}
