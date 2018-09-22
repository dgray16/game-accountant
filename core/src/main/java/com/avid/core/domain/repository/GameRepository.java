package com.avid.core.domain.repository;

import com.avid.core.domain.model.entity.Game;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface GameRepository extends ReactiveMongoRepository<Game, ObjectId> {
}
