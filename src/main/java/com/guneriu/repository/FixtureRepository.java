package com.guneriu.repository;

import com.guneriu.model.Fixture;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ugur on 14.05.2016.
 */
@Repository
public interface FixtureRepository extends MongoRepository<Fixture, Long> {

    List<Fixture> findByLeagueId(Long leagueId);

    List<Fixture> findByTeamId(Long teamId);
}
