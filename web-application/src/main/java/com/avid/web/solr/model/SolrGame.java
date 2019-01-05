package com.avid.web.solr.model;

import com.avid.core.domain.model.entity.Game;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@Getter
@Setter
@SolrDocument(collection = "game")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SolrGame {

    @Id
    @Indexed(name = "id", type = "string")
    String id;

    @Indexed(name = "name", type = "string")
    String name;

    public static SolrGame of(Game game) {
        SolrGame result = new SolrGame();

        result.setName(game.getName());
        result.setId(game.getId().toHexString());

        return result;
    }
}
