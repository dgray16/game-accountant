package com.avid.core.domain.service.base;

import com.avid.core.domain.model.base.AbstractIdentifiable;
import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Default implementation of {@link CrudSupport} which simply delegates
 * CRUD operations to {@link org.springframework.data.repository.Repository}.
 */
@AllArgsConstructor
public abstract class DefaultCrudSupport<E extends AbstractIdentifiable> implements CrudSupport<E> {

    private ReactiveCrudRepository<E, ObjectId> repository;

    @Override
    public Mono<E> findById(ObjectId entityId) {
        return repository.findById(entityId);
    }

    @Override
    public Mono<E> getById(ObjectId entityId) {
        return repository
                .findById(entityId)
                .doOnError(error -> new EmptyResultDataAccessException("Entity was not found by ID: " + entityId, 1));
    }

    @Override
    public Flux<E> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<E> findAll(Example<E> example) {
        return ((ReactiveMongoRepository<E, ObjectId>) repository).findAll(example);
    }

    @Override
    public Flux<E> findAll(Collection<ObjectId> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Mono<E> update(E entity) {
        Preconditions.checkArgument(
                Objects.nonNull(entity.getId()), "Could not update entity. Entity hasn't persisted yet"
        );
        return repository.save(entity);
    }

    @Override
    public Mono<E> create(E entity) {
        Preconditions.checkArgument(
                Objects.isNull(entity.getId()), "Could not create entity. Entity has already exists"
        );
        return repository.save(entity);
    }

    @Override
    @SneakyThrows
    public Mono<E> create(Consumer<E> consumer) {
        TypeToken<E> typeToken = new TypeToken<>(getClass()) {};
        E entityToBeCreated = (E) typeToken.getRawType().getDeclaredConstructor().newInstance();
        consumer.accept(entityToBeCreated);
        return create(entityToBeCreated);
    }

    @Override
    public Mono<E> save(E entity) {
        return Objects.isNull(entity.getId()) ? create(entity) : update(entity);
    }

    @Override
    public Mono<Void> delete(E entity) {
        Preconditions.checkArgument(
                Objects.nonNull(entity.getId()), "Could not delete entity. Entity hasn't persisted yet"
        );
        return repository.delete(entity);
    }

    @Override
    public Mono<Void> delete(ObjectId id) {
        return repository.deleteById(id);
    }
}
