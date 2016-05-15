package com.guneriu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * Created by ugur on 14.05.2016.
 */
@Data
@Document(collection = "teams")
public class Team extends BaseModel {

    @Id
    private Long id;

    private Long leagueId;

    private String name;

    private String code;

    private String shortName;

    private String squadMarketValue;

    private String crestUrl;

    public void extractId() {
        Map<String, Map<String, String>> links = this.getLinks();
        links.entrySet().stream().filter(e -> e.getKey().equals("self")).forEach(e -> {
            String href = e.getValue().get("href");
            String[] paths = href.split("/");
            this.setId(Long.valueOf(paths[paths.length - 1]));
        });
    }

}
