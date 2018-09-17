package com.avid.core.domain.model.entity;

import com.avid.core.domain.model.base.AbstractIdentifiable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Typical user of application.
 */
@Getter
@Setter
@Document(collection = "players")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Player extends AbstractIdentifiable {

    String email;

}

