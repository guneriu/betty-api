package com.guneriu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.guneriu.config.ServiceConfig;
import com.guneriu.model.League;
import com.guneriu.service.FootballSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@Slf4j
public class BettyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BettyApiApplication.class, args);
	}

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ServiceConfig config;

	@Autowired
	private FootballSyncService footballSyncService;


	@Bean
	CommandLineRunner sync() {
		ObjectMapper mapper = new ObjectMapper();
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			headers.setAll(config.getHeaders());
			List<League> leagues = mongoTemplate.findAll(League.class);

			if (leagues.size() == 0) {

				HttpEntity<String> entity = new HttpEntity<>(headers);
				log.info("making request to {}, headers {}", config.getUrl() + "soccerseasons", headers);
				ResponseEntity<List<League>> responseEntity = restTemplate.exchange(config.getUrl() + "soccerseasons", HttpMethod.GET, entity, new ParameterizedTypeReference<List<League>>() {
				});

				if (responseEntity.getStatusCode().is2xxSuccessful()) {
					leagues = responseEntity.getBody();
					leagues.forEach(mongoTemplate::save);
				}
			}
		return args -> footballSyncService.sycnleagues();
	}

//	@Bean
//	CommandLineRunner runner() {
//		return args -> {
//
//			ObjectMapper mapper = new ObjectMapper();
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.setAll(config.getHeaders());
//			List<League> leagues = mongoTemplate.findAll(League.class);
//
//			if (leagues.size() == 0) {
//
//				HttpEntity<String> entity = new HttpEntity<>(headers);
//				log.info("making request to {}, headers {}", config.getUrl() + "soccerseasons", headers);
//				ResponseEntity<List<League>> responseEntity = restTemplate.exchange(config.getUrl() + "soccerseasons", HttpMethod.GET, entity, new ParameterizedTypeReference<List<League>>() {
//				});
//
//				if (responseEntity.getStatusCode().is2xxSuccessful()) {
//					leagues = responseEntity.getBody();
//					leagues.forEach(mongoTemplate::save);
//				}
//			}
//
//			Set<String> processedUrls = new HashSet<>();
//			leagues.forEach(s -> this.extract(mapper, headers, s.getLinks(), processedUrls));
//
//
////			leagues.stream().forEach(league -> {
////				Map<String, Map<String, String>> links = league.getLinks();
////
////
////				extract(mapper, headers, links);
////
////
////				String teamsUrl = links.get("teams").get("href");
////
////				HttpEntity<String> entity = new HttpEntity<>(headers);
////				log.info("making request to {}, headers {}", teamsUrl, headers);
////				ResponseEntity<TeamResult> responseEntity = restTemplate.exchange(teamsUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<TeamResult>() {
////				});
////
////				if (responseEntity.getStatusCode().is2xxSuccessful()) {
////					mongoTemplate.save(responseEntity.getBody());
////				}
////
////			});
//
//
//		};
//	}

//	private void extract(ObjectMapper mapper, MultiValueMap<String, String> headers, Map<String, Map<String, String>> links, Set<String> processedUrls ) {
//		links.entrySet().stream().filter(e -> !e.getKey().equals("self"))
//                .forEach(entry -> {
//					log.info("links: {}", links);
//                    HttpEntity<String> entity = new HttpEntity<>(headers);
//                    String href = entry.getValue().get("href");
//                    log.info("making request to {}, headers {}", href, headers);
//					if (!processedUrls.contains(href)) {
//						ResponseEntity<String> responseEntity = null;
//						try {
////							log.info("will sleep for 1 sec");
////							Thread.sleep(1000L);
////							log.info("woke up");
//							responseEntity = restTemplate.exchange(href, HttpMethod.GET, entity, String.class);
//							processedUrls.add(href);
//							log.info("response status: {}", responseEntity.getStatusCode());
//
//							if (responseEntity.getStatusCode().is2xxSuccessful()) {
//								List<Map<String, Map<String, String>>> linkList = new ArrayList<>();
//								String body = responseEntity.getBody();
//								log.info("result: {}", body);
//
//								linkList = getLinks(entry.getKey(), body, mapper);
//
//								mongoTemplate.save(body, entry.getKey());
//
//								if (!linkList.isEmpty()) {
//									linkList.forEach(m -> this.extract(mapper, headers, m, processedUrls));
//								}
//							}
//						} catch (Exception e) {
//							log.info("resource not found {}", href);
//							e.printStackTrace();
//						}
//
//					}
//
//                });
//	}
//
//	public List<Map<String, Map<String, String>>> getLinks(String resource, String responseBody, ObjectMapper mapper) {
//		if (resource.equals("players")) {
//			try {
//				PlayersBaseModel playersBaseModel = mapper.readValue(responseBody, PlayersBaseModel.class);
//				return playersBaseModel.getAllLinks();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		if (resource.equals("teams")) {
//			try {
//				TeamsBaseModel playersBaseModel = mapper.readValue(responseBody, TeamsBaseModel.class);
//				return playersBaseModel.getAllLinks();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		if (resource.equals("fixtures")) {
//			try {
//				FixturesBaseModel playersBaseModel = mapper.readValue(responseBody, FixturesBaseModel.class);
//				return playersBaseModel.getAllLinks();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		if (resource.equals("leagueTable")) {
//			try {
//				LeaguesBaseModel playersBaseModel = mapper.readValue(responseBody, LeaguesBaseModel.class);
//				return playersBaseModel.getAllLinks();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return new ArrayList<>();
//	}

}
