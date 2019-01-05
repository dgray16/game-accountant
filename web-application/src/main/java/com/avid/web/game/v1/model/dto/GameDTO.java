package com.avid.web.game.v1.model.dto;

import com.avid.core.domain.model.dictionary.GameGenre;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameDTO extends ResourceSupport {

    String name;
    List<GameGenre> gameGenres;

}
