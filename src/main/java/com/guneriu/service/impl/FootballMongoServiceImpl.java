package com.guneriu.service.impl;

import com.guneriu.model.Fixture;
import com.guneriu.model.League;
import com.guneriu.model.Player;
import com.guneriu.model.Team;
import com.guneriu.repository.FixtureRepository;
import com.guneriu.repository.LeagueRepository;
import com.guneriu.repository.PlayerRepository;
import com.guneriu.repository.TeamRepository;
import com.guneriu.service.FootballMongoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by ugur on 14.05.2016.
 */
@Service
@Slf4j
public class FootballMongoServiceImpl implements FootballMongoService {


    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private FixtureRepository fixtureRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;


    @Override
    public List<League> getLeagues() {
        return leagueRepository.findAll();
    }

    @Override
    public List<Team> getTeamsOfLeague(Long leagueId) {
        return teamRepository.findByLeagueId(leagueId);
    }

    @Override
    public List<Fixture> getFixturesOfLeague(Long leagueId) {
        return fixtureRepository.findByLeagueId(leagueId);
    }

    @Override
    public Team getTeam(Long teamId) {
        return teamRepository.findOne(teamId);
    }

    @Override
    public List<Fixture> getFixturesOfTeam(Long teamId) {
        return fixtureRepository.findByTeamId(teamId);
    }

    @Override
    public List<Player> getPlayersOfTeam(Long teamId) {
        return playerRepository.findByTeamId(teamId);
    }

    @Override
    public List<Fixture> getFixtures() {
        return fixtureRepository.findAll();
    }

    @Override
    public Fixture getFixture(Long fixtureId) {
        return fixtureRepository.findOne(fixtureId);
    }

}
