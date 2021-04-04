package com.codeworrisors.Movie_Community_Web.component;

import com.codeworrisors.Movie_Community_Web.dto.BoxOfficeRankingDto;
import com.codeworrisors.Movie_Community_Web.model.BoxOfficeRanking;
import com.codeworrisors.Movie_Community_Web.repository.BoxOfficeRankingRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.codeworrisors.Movie_Community_Web.property.BoxOfficeRankingProperties.*;

@RequiredArgsConstructor
@Component
public class BoxOfficeRankingScheduledTask {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BoxOfficeRankingRepository boxOfficeRankingRepository;

//    @PostConstruct
//    public void initRankingData() {
//        recordBoxOfficeRanking();
//    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void updateRankingData() {
        recordBoxOfficeRanking();
    }

    public void recordBoxOfficeRanking() {
        logger.info("Box Office Ranking API call");
        //하루전 통계자료만 있다.
        LocalDate date = LocalDate.now().minusDays(1);
        String dataFormatToAPI = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        HttpHeaders headers = new HttpHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BOX_OFFICE_RANKING_API_URL)
                .queryParam("key", BOX_OFFICE_RANKING_API_KEY)
                .queryParam("targetDt", dataFormatToAPI);

        URI queryUri = builder.build().toUri();
        HttpEntity<String> entity = new HttpEntity<String>("", null);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(queryUri, HttpMethod.GET, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
            jsonNode = jsonNode.get("boxOfficeResult");
            List<BoxOfficeRankingDto> rankingList = objectMapper.readValue(jsonNode.get("dailyBoxOfficeList").toString(), new TypeReference<List<BoxOfficeRankingDto>>() {
            });

            rankingList.forEach(boxOfficeRankingDto -> {
                boxOfficeRankingRepository.save(new BoxOfficeRanking(
                        boxOfficeRankingDto.getMovieNm(),
                        boxOfficeRankingDto.getRank(),
                        boxOfficeRankingDto.getRankInten(),
                        LocalDate.now().minusDays(1)));
            });
            logger.info("ranking save - date: " + date.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
