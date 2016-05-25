package com.guneriu.controller;

import com.guneriu.model.Fixture;
import com.guneriu.model.League;
import com.guneriu.model.Player;
import com.guneriu.model.Team;
import com.guneriu.service.FootballMongoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ugur on 14.05.2016.
 */
@RestController
@RequestMapping("/api/soccer")
public class FootballController {

    @Autowired
    private FootballMongoService footballMongoService;


    @RequestMapping(value = "/leagues", method = RequestMethod.GET)
    public List<League> getLeagues() {
        return footballMongoService.getLeagues();
    }

    @RequestMapping(value = "/leagues/{leagueId}/teams", method = RequestMethod.GET)
    public List<Team> getTeamsOfLeague(@PathVariable("leagueId") Long leagueId) {
        return footballMongoService.getTeamsOfLeague(leagueId);
    }

    @RequestMapping(value = "/teams/{teamId}", method = RequestMethod.GET)
    public Team getTeam(@PathVariable("teamId") Long teamId) {
        return footballMongoService.getTeam(teamId);
    }

    @RequestMapping(value = "/leagues/{leagueId}/fixtures", method = RequestMethod.GET)
    public List<Fixture> getFixturesOfLeague(@PathVariable("leagueId") Long leagueId) {
        return footballMongoService.getFixturesOfLeague(leagueId);
    }

    @RequestMapping(value = "/teams/{teamId}/players", method = RequestMethod.GET)
    public List<Player> getPlayersOfTeam(@PathVariable("teamId") Long teamId) {
        return footballMongoService.getPlayersOfTeam(teamId);
    }

    @RequestMapping(value = "/teams/{teamId}/fixtures", method = RequestMethod.GET)
    public List<Fixture> getFixturesOfTeam(@PathVariable("teamId") Long teamId) {
        return footballMongoService.getFixturesOfTeam(teamId);
    }


    @RequestMapping(value = "/fixtures", method = RequestMethod.GET)
    public List<Fixture> getFixtures() {
        return footballMongoService.getFixtures();
    }

    @RequestMapping(value = "/fixtures/{fixtureId}", method = RequestMethod.GET)
    public Fixture getFixture(@PathVariable("fixtureId") Long fixtureId) {
        return footballMongoService.getFixture(fixtureId);
    }

//    @RequestMapping(value = "/seasons/{leagueId}/fixtures", method = RequestMethod.GET)
//    public void getSeasons(@PathVariable("leagueId") Long leagueId) {
//        return footballRestService.getFixturesOfLeague(leagueId);
//    }



}
