package com.avid.web.solr.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "game")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SolrGame {

    @Id
    @Indexed(name = "id", type = "string")
    String id;

    @Indexed(name = "name", type = "string")
    String name;

}
