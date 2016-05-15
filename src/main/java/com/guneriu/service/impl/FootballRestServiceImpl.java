package com.guneriu.service.impl;

import com.guneriu.config.ServiceConfig;
import com.guneriu.message.FixturesResponse;
import com.guneriu.message.PlayersResponse;
import com.guneriu.message.TeamsResponse;
import com.guneriu.model.Fixture;
import com.guneriu.model.League;
import com.guneriu.model.Player;
import com.guneriu.model.Team;
import com.guneriu.service.FootballRestService;
import com.guneriu.util.FootballRestUtil;
import com.guneriu.util.PathBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.guneriu.model.Constants.*;

/**
 * Created by ugur on 14.05.2016.
 */
@Service
@Slf4j
public class FootballRestServiceImpl implements FootballRestService {

    @Autowired
    private ServiceConfig config;

    @Autowired
    private RestTemplate restTemplate;

    private FootballRestUtil restUtil;

    @PostConstruct
    public void setRestTemplate() {
        restUtil = new FootballRestUtil(restTemplate);
    }

    @Override
    public List<League> getLeagues() {
        String url = PathBuilder.build(config.getUrl(), SEASONS);
        return restUtil.makeRequest(url, config.getHeaders(), new ParameterizedTypeReference<List<League>>() {});
    }

    @Override
    public List<Team> getTeamsOfLeague(Long leagueId) {
        String url = PathBuilder.build(config.getUrl(), SEASONS, leagueId.toString(), TEAMS);
        //TODO extract id from links
        return restUtil.makeRequest(url, config.getHeaders(), TeamsResponse.class).getTeams();
    }

    @Override
    public List<Fixture> getFixturesOfLeague(Long leagueId) {
        String url = PathBuilder.build(config.getUrl(), SEASONS, leagueId.toString(), FIXTURES);
        //TODO extract homeTeamId and awayTeamId from links
        return restUtil.makeRequest(url, config.getHeaders(), FixturesResponse.class).getFixtures();
    }

    @Override
    public Team getTeam(Long teamId) {
        String url = PathBuilder.build(config.getUrl(), TEAMS, teamId.toString());
        return restUtil.makeRequest(url, config.getHeaders(), Team.class);
    }

    @Override
    public List<Fixture> getFixturesOfTeam(Long teamId) {
        String url = PathBuilder.build(config.getUrl(), TEAMS, teamId.toString(), FIXTURES);
        return restUtil.makeRequest(url, config.getHeaders(), FixturesResponse.class).getFixtures();
    }

    @Override
    public List<Player> getPlayersOfTeam(Long teamId) {
        String url = PathBuilder.build(config.getUrl(), TEAMS, teamId.toString(), PLAYERS);
        return restUtil.makeRequest(url, config.getHeaders(), PlayersResponse.class).getPlayers();
    }

    @Override
    public List<Fixture> getFixtures() {
        String url = PathBuilder.build(config.getUrl(), FIXTURES);
        //FIXME examine and correct return type
        return restUtil.makeRequest(url, config.getHeaders(), new ParameterizedTypeReference<List<Fixture>>() {});
    }

}
