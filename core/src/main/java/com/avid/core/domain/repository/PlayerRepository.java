package com.avid.core.domain.repository;

import com.avid.core.domain.model.entity.Player;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PlayerRepository extends ReactiveMongoRepository<Player, ObjectId> {

    Mono<Player> findFirstByEmail(String email);

}
