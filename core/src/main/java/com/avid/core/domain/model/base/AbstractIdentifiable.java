package com.avid.core.domain.model.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base class for all application entities which have unique identifier.
 */
@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class AbstractIdentifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
}
