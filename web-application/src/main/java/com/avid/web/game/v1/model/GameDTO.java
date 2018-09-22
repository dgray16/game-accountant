package com.avid.web.game.v1.model;

import com.avid.core.domain.model.dictionary.GameGenre;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameDTO {

    String name;
    GameGenre gameGenre;

}
