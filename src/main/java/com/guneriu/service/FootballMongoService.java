package com.guneriu.service;

import com.guneriu.model.Fixture;
import com.guneriu.model.League;
import com.guneriu.model.Player;
import com.guneriu.model.Team;

import java.util.List;

public interface FootballMongoService {

    List<League> getLeagues();

    List<Team> getTeamsOfLeague(Long leagueId);

    List<Fixture> getFixturesOfLeague(Long leagueId);

    Team getTeam(Long teamId);

    List<Fixture> getFixturesOfTeam(Long teamId);

    List<Player> getPlayersOfTeam(Long teamId);

    List<Fixture> getFixtures();

    Fixture getFixture(Long fixtureId);
}
