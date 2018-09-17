package com.avid.core.domain.model.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * Base class for all application entities which have unique identifier.
 */
@Getter
@Setter
@ToString
public abstract class AbstractIdentifiable {

    @Id
    protected ObjectId id;
}
