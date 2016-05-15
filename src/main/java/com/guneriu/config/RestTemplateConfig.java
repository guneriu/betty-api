package com.guneriu.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ugur on 14.05.2016.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverterList = new ArrayList<>();

        restTemplate.getMessageConverters().stream()
                .filter(c -> c.getClass().equals(MappingJackson2HttpMessageConverter.class))
                .forEach(messageConverterList::add);


        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(new Jackson2ObjectMapperBuilder().build().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));

        jackson2HttpMessageConverter.setObjectMapper(GKObjectMapper.getInstance());

        messageConverterList.add(jackson2HttpMessageConverter);

        restTemplate.setMessageConverters(messageConverterList);

//        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build()
//                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//                .configure(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS, true);



        return restTemplate;
    }
}
