package com.guneriu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * Created by ugur on 14.05.2016.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "service.football")
public class ServiceConfig {

    private String url;

    private Map<String, String> headers;
}
