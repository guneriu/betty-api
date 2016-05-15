package com.guneriu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * Created by ugur on 14.05.2016.
 */
@Data
@Document(collection = "leagues")
public class League extends BaseModel {

    @Id
    private Long id;

    private String caption;

    private String league;

    private Integer year;

    @JsonProperty(value = "currentMatchday")
    private Integer currentMatchDay;

    private Integer numberOfMatchdays;

    private Integer numberOfTeams;

    private Integer numberOfGames;

    private String lastUpdated;

}
