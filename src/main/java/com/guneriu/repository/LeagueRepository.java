package com.guneriu.repository;

import com.guneriu.model.League;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ugur on 14.05.2016.
 */
@Repository
public interface LeagueRepository extends MongoRepository<League, Long> {
}
