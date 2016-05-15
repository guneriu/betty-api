package com.guneriu.repository;

import com.guneriu.model.League;
import com.guneriu.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ugur on 14.05.2016.
 */
@Repository
public interface PlayerRepository extends MongoRepository<Player, Long> {
}
