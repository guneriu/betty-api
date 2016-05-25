package com.guneriu.service.impl;

import com.guneriu.model.Fixture;
import com.guneriu.model.League;
import com.guneriu.model.Player;
import com.guneriu.model.Team;
import com.guneriu.repository.FixtureRepository;
import com.guneriu.repository.LeagueRepository;
import com.guneriu.repository.PlayerRepository;
import com.guneriu.repository.TeamRepository;
import com.guneriu.service.FootballRestService;
import com.guneriu.service.FootballSyncService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by ugur on 14.05.2016.
 */
@Service
@Slf4j
public class FootballSyncServiceImpl implements FootballSyncService {

    @Autowired
    private FootballRestService restService;

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private FixtureRepository fixtureRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public void sycnleagues() {

        Set<Fixture> fixtures = Collections.synchronizedSet(new HashSet<>());
        Set<Team> teams =Collections.synchronizedSet(new HashSet<>());
        Set<Player> players = Collections.synchronizedSet(new HashSet<>());

//        List<League> leagues = restService.getLeagues();
        List<League> leagues = leagueRepository.findAll();

        leagues.stream().forEach(league -> {
            Long leagueId = league.getId();
            List<Team> teamsOfLeague = restService.getTeamsOfLeague(leagueId);

            teamsOfLeague.stream().forEach(team -> {
                team.setLeagueId(leagueId);
                team.extractId();

                Long teamId = team.getId();
                List<Fixture> fixturesOfTeam = restService.getFixturesOfTeam(teamId);
//
                fixturesOfTeam.forEach(f -> {f.extractId(); f.setTeamId(teamId); f.extractTeamIds();});
//
                fixtures.addAll(fixturesOfTeam);
//
//                List<Player> playersOfTeam = restService.getPlayersOfTeam(teamId);
//
//                playersOfTeam.forEach(p -> p.setTeamId(teamId));
//
//                players.addAll(playersOfTeam);
            });

            teams.addAll(teamsOfLeague);

            List<Fixture> fixturesOfLeague = restService.getFixturesOfLeague(leagueId);

            fixturesOfLeague.stream().forEach(fixture -> {
                fixture.extractId();
                fixture.setLeagueId(leagueId);
                fixture.extractTeamIds();
            });

//            fixturesOfLeague.forEach(fixture -> fixture.setLeagueId(leagueId));

            //TODO leagueTable


        });


        log.info("found {} league, {} team {} player {} fixture", leagues.size(), teams.size(), players.size(), fixtures.size());

        leagueRepository.save(leagues);
        playerRepository.save(players);
        fixtureRepository.save(fixtures);
        teamRepository.save(teams);

    }

    public void sycnTeam(Long leagueId) {

        List<League> leagues = restService.getLeagues();

    }


}
