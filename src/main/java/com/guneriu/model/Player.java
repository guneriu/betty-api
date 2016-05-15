package com.guneriu.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;

/**
 * Created by ugur on 14.05.2016.
 */
@Data
@Document(collection = "players")
public class Player extends BaseModel {

    private Long teamId;

    private String name;

    private String position;

    private String jerseyNumber;

    private String dateOfBirth;

    private String nationality;

    private String contractUntil;

    private String marketValue;

}
