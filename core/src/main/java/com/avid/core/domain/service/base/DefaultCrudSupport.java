package com.avid.core.domain.service.base;

import com.avid.core.domain.model.base.AbstractIdentifiable;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Default implementation of {@link CrudSupport} which simply delegates
 * CRUD operations to {@link CrudRepository}.
 */
@AllArgsConstructor
public abstract class DefaultCrudSupport<E extends AbstractIdentifiable> implements CrudSupport<E> {

    private CrudRepository<E, Long> repository;

    @Override
    public Optional<E> findById(Long entityId) {
        return repository.findById(entityId);
    }

    @Override
    public E getById(Long entityId) {
        return repository
                .findById(entityId)
                .orElseThrow(() -> new EmptyResultDataAccessException("Entity was not found by ID: " + entityId, 1));
    }

    @Override
    public List<E> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        return ((MongoRepository<E, Long>) repository).findAll(pageable);
    }

    @Override
    public List<E> findAll(Collection<Long> ids) {
        return Lists.newArrayList(repository.findAllById(ids));
    }

    @Override
    public E update(E entity) {
        Preconditions.checkArgument(
                Objects.nonNull(entity.getId()), "Could not update entity. Entity hasn't persisted yet"
        );
        return repository.save(entity);
    }

    @Override
    public E create(E entity) {
        Preconditions.checkArgument(
                Objects.isNull(entity.getId()), "Could not create entity. Entity has already exists"
        );
        return repository.save(entity);
    }

    @Override
    public E save(E entity) {
        return Objects.isNull(entity.getId()) ? create(entity) : update(entity);
    }

    @Override
    public void delete(E entity) {
        Preconditions.checkArgument(
                Objects.nonNull(entity.getId()), "Could not delete entity. Entity hasn't persisted yet"
        );
        repository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
