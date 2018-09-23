package com.avid.web.config.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * Describes custom relatioships names for HATEAOS REST.
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum HATEOASRelationship {

    DELETE("delete");

    String value;

}
