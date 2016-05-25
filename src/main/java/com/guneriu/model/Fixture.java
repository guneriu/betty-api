package com.guneriu.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

import lombok.Data;

/**
 * Created by ugur on 14.05.2016.
 */
@Data
@Document(collection = "fixtures")
public class Fixture extends BaseModel {

    @Id
    private Long id;

    private Long leagueId;

    private Long teamId;

    private String date;

    @JsonProperty(value = "matchday")
    private Integer matchDay;

    private String homeTeamName;

    private String awayTeamName;

    private Long homeTeamId;

    private Long awayTeamId;

    private Score result;

    @Data
    class Score {
        private Integer goalsHomeTeam;

        private Integer goalsAwayTeam;
    }

    public void extractId() {
        Map<String, Map<String, String>> links = this.getLinks();
        links.entrySet().stream().filter(e -> e.getKey().equals("self")).forEach(e -> {
            String href = e.getValue().get("href");
            String[] paths = href.split("/");
            this.setId(Long.valueOf(paths[paths.length - 1]));
        });
    }

    public void extractTeamIds() {
        Map<String, Map<String, String>> links = this.getLinks();
        links.entrySet().stream().filter(e -> e.getKey().equals("homeTeam") || e.getKey().equals("awayTeam")).forEach(e -> {
            String href = e.getValue().get("href");
            String[] paths = href.split("/");
            Long idOfTeam = Long.valueOf(paths[paths.length - 1]);
            if (e.getKey().equals("homeTeam")) {
                this.setHomeTeamId(idOfTeam);
            } else if (e.getKey().equals("awayTeam")) {
                this.setAwayTeamId(idOfTeam);
            }
        });
    }

}
