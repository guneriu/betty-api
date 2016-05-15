package com.guneriu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * Created by ugur on 14.05.2016.
 */
@Data
public class BaseModel {

//    @JsonIgnore
    @JsonProperty(value = "_links")
    private Map<String, Map<String, String>> links;
}
