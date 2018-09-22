package com.avid.core.domain.model.entity;

import com.avid.core.domain.model.dictionary.GameGenre;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "games")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Game {

    String name;
    GameGenre genre;

}
