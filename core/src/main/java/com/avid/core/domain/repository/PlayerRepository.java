package com.avid.core.domain.repository;

import com.avid.core.domain.model.entity.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<Player, Long> {
}
