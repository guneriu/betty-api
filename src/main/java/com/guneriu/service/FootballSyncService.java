package com.guneriu.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guneriu.message.ExceptionResponse;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.util.List;

/**
 * Created by ugur on 14.05.2016.
 */
public interface FootballSyncService {
    void sycnleagues();


    public static void main(String[] args) throws IOException {
        String text = "[{\"error\": \"hede\"}]";

        ObjectMapper mapper = new Jackson2ObjectMapperBuilder().build()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//        List<String> value = mapper.readValue(text, new TypeReference<List<String>>() {
//        });
        Object value = (String) mapper.readValue(text, Object.class);

        System.out.println(value);
    }
}
