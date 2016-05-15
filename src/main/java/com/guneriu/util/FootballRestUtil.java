package com.guneriu.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guneriu.config.GKObjectMapper;
import com.guneriu.message.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ugur on 14.05.2016.
 */
@Slf4j
public class FootballRestUtil {

    private RestTemplate restTemplate;

    private ObjectMapper objectMapper;

    private String SECONDS_LEFT_FOR_REQUEST_HEADER = "X-RequestCounter-Reset";

    private String AVAILABLE_REQUEST_COUNT_HEADER = "X-Requests-Available";

    public FootballRestUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = GKObjectMapper.getInstance();
//        this.objectMapper = new Jackson2ObjectMapperBuilder().build()
//                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//                .configure(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS, true);
    }

    public <T> T makeRequest(String url, Map<String, String> headers, Class<T> responseClass) {
        MultiValueMap<String, String> headersMultiValueMap = new LinkedMultiValueMap<>();
        headersMultiValueMap.setAll(headers);

        HttpEntity<String> entity = new HttpEntity<>(headersMultiValueMap);
        log.info("making request to {}, headers {}", url, headers);
        ResponseEntity<T> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, responseClass);
            if (isFailed(responseEntity)) {
                return makeRequest(url, headers, responseClass);
            } else {
                return responseEntity.getBody();
            }
        } catch (RestClientException e) {
            log.error("request failed {}", e);
        }
        return null;
    }

    public <T> T makeRequest(String url, Map<String, String> headers, ParameterizedTypeReference<T> typeReference) {
        MultiValueMap<String, String> headersMultiValueMap = new LinkedMultiValueMap<>();
        headersMultiValueMap.setAll(headers);

        HttpEntity<String> entity = new HttpEntity<>(headersMultiValueMap);
        log.info("making request to {}, headers {}", url, headers);
        ResponseEntity<T> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, typeReference);
            if (isFailed(responseEntity)) {
                return makeRequest(url, headers, typeReference);
            } else {
                return responseEntity.getBody();
            }
        } catch (RestClientException e) {
            log.error("request failed {}", e);
        }
        return null;
    }

    private <T> boolean isFailed(ResponseEntity<T> responseEntity) {

        Map<String, String> headers = responseEntity.getHeaders().toSingleValueMap();
        if (Integer.parseInt(headers.get(AVAILABLE_REQUEST_COUNT_HEADER)) > 0 && responseEntity.getStatusCode().is2xxSuccessful()) {
            return false;
        }

        int secondsForNextRequest = Integer.parseInt(headers.get(SECONDS_LEFT_FOR_REQUEST_HEADER));

        try {
//            ExceptionResponse exceptionResponse = objectMapper.readValue((String) responseEntity.getBody(), ExceptionResponse.class);
//            if (exceptionResponse.isTooManyRequestError()) {
                log.info("too many request error, sleeping for {} seconds", secondsForNextRequest);
                Thread.sleep(secondsForNextRequest * 1000);
                return true;
//            }
        } catch (InterruptedException e) {
            log.error("failed to sleep", e);
        }

        return false;
    }
}
