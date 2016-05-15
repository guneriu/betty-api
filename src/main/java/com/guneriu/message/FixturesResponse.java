package com.guneriu.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.guneriu.model.Fixture;
import com.guneriu.model.Team;
import lombok.Data;

import java.util.List;

/**
 * Created by ugur on 14.05.2016.
 */
@Data
public class FixturesResponse extends BaseResponse {

    private List<Fixture> fixtures;
}
