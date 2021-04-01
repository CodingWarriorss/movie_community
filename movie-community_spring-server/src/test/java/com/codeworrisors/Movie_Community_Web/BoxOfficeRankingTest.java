package com.codeworrisors.Movie_Community_Web;

import com.codeworrisors.Movie_Community_Web.dto.BoxOfficeRankingDto;
import com.codeworrisors.Movie_Community_Web.model.BoxOfficeRanking;
import com.codeworrisors.Movie_Community_Web.repository.BoxOfficeRankingRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
public class BoxOfficeRankingTest {

    @Autowired
    private BoxOfficeRankingRepository boxOfficeRankingRepository;

    @Test
    public void insertTest() {
        LocalDate date = LocalDate.now().minusDays(1);
        System.out.println(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));


        BoxOfficeRanking boxOfficeRanking = new BoxOfficeRanking("test", 1, "0", date);

        boxOfficeRankingRepository.save(boxOfficeRanking);

    }

    @Test
    public void receiveDataTest() {
        LocalDate date = LocalDate.now().minusDays(1);
        String apiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
        HttpHeaders headers = new HttpHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("key", "d9ca373c6f415c5ac0cda4b1b28a6e07")
                .queryParam("targetDt", date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        URI queryUri = builder.build().toUri();
        HttpEntity<String> entity = new HttpEntity<String>("", null);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(queryUri, HttpMethod.GET, entity, String.class);

        System.out.println(responseEntity.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
            jsonNode = jsonNode.get("boxOfficeResult");

            List<BoxOfficeRankingDto> rankingList = objectMapper.readValue(jsonNode.get("dailyBoxOfficeList").toString(), new TypeReference<List<BoxOfficeRankingDto>>() {
            });

            for (BoxOfficeRankingDto boxOfficeRankingDTO : rankingList) {
                System.out.println(boxOfficeRankingDTO.getMovieNm());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void boxOfficeRankingSearchTest() {
        List<BoxOfficeRanking> testList = boxOfficeRankingRepository.findAllByTargetDt(LocalDate.now().minusDays(1));
        testList.forEach(boxOfficeRanking -> {
            System.out.println(boxOfficeRanking.toString());
        });
    }
}
