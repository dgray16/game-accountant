package com.avid.core.domain.repository;

import com.avid.core.domain.model.entity.Player;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PlayerRepository extends ReactiveMongoRepository<Player, Long> {
}
