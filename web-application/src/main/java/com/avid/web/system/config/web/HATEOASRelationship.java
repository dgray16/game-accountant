package com.avid.web.system.config.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * Describes custom relationships names for HATEAOS REST.
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum HATEOASRelationship {

    SELF("self"),
    DELETE("delete");

    String value;

}
