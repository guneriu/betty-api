package com.guneriu.controller;

import com.guneriu.model.League;
import com.guneriu.service.FootballRestService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private FootballRestService footballRestService;


    @RequestMapping(value = "/seasons", method = RequestMethod.GET)
    public List<League> getSeasons() {
        return footballRestService.getLeagues();
    }

//    @RequestMapping(value = "/seasons/{leagueId}/fixtures", method = RequestMethod.GET)
//    public void getSeasons(@PathVariable("leagueId") Long leagueId) {
//        return footballRestService.getFixturesOfLeague(leagueId);
//    }



}
