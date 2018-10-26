package com.avid.core.domain.service.base;

import com.avid.core.domain.model.base.AbstractIdentifiable;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

/**
 * Defines commonly used CRUD operations.
 */
public interface CrudSupport<E extends AbstractIdentifiable> {

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Mono<E> findById(final ObjectId entityId);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id
     * @throws org.springframework.dao.EmptyResultDataAccessException if entity is not found.
     */
    Mono<E> getById(final ObjectId entityId);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Flux<E> findAll();

    /**
     * TODO verify this method
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param example offsets and limits
     * @return a page of entities
     */
    Flux<E> findAll(Example<E> example);

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids preferable implementation is {@link java.util.Set}
     * @return {@link List<E>} collection of entities
     */
    Flux<E> findAll(Collection<ObjectId> ids);

    /**
     * Updates an entity.
     * If you want to use one method for <strong>create</strong> and <strong>update</strong>
     * operations use {@link #save(AbstractIdentifiable)} instead.
     *
     * @param entity entity to update
     * @return updated entity
     * @throws IllegalArgumentException if entity hasn't persisted yet
     */
    Mono<E> update(final E entity);

    /**
     * Persists an entity.
     *
     * @param entity entity to create
     * @return created entity
     * @throws IllegalArgumentException if entity has already persisted
     */
    Mono<E> create(final E entity);

    /**
     * Saves an entity.
     * This method resolves entity's state and
     * delegates to either {@link #create(AbstractIdentifiable)} or {@link #update(AbstractIdentifiable)}
     * method to do actual work.
     *
     * @param entity entity to save
     * @return saved entity
     */
    Mono<E> save(final E entity);

    /**
     * Removes an entity.
     *
     * @param entity entity to remove
     * @return mono object to support reactive approach
     * @throws IllegalArgumentException if entity hasn't persisted yet
     */
    Mono<Void> delete(final E entity);

    /**
     * Removes an entity by it's identifier.
     *
     * @param id unique identifier
     * @return mono object to support reactive approach
     */
    Mono<Void> delete(final ObjectId id);
}
