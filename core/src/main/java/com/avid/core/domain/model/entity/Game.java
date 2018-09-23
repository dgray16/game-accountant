package com.avid.core.domain.model.entity;

import com.avid.core.domain.model.base.AbstractIdentifiable;
import com.avid.core.domain.model.dictionary.GameGenre;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "games")
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class Game extends AbstractIdentifiable {

    String name;
    List<GameGenre> genres;

}
