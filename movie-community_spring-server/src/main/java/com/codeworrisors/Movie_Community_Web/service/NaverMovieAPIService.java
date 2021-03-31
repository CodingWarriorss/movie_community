package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.property.NaverApiProperties;
import com.codeworrisors.Movie_Community_Web.dto.NaverAPIResponseDto;
import com.codeworrisors.Movie_Community_Web.dto.NaverMovieDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
public class NaverMovieAPIService {

    public String searchMovie(String title) throws IOException {
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", NaverApiProperties.CLIENT_ID);
        requestHeaders.put("X-Naver-Client-Secret", NaverApiProperties.CLIENT_SECRET);
        return get(NaverApiProperties.API_URL + title, requestHeaders);
    }

    private String get(String apiUrl, Map<String, String> requestHeaders) throws IOException {
        // 요청
        HttpURLConnection conn = connect(apiUrl);
        try {
            conn.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }

            // 응답
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출

                return readBody(conn.getInputStream());
            } else { // 에러 발생
                return readBody(conn.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            conn.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        ObjectMapper jsonParser = new ObjectMapper();

        //Jaskson 사용 Deserializing? 후 sorting 다시 serializing
        try {
            NaverAPIResponseDto naverAPIResponseDto = jsonParser.readValue(body, NaverAPIResponseDto.class);
            naverAPIResponseDto.getItems().sort(new Comparator<NaverMovieDto>() {
                @Override
                public int compare(NaverMovieDto o1, NaverMovieDto o2) {
                    double o1Rating = Double.parseDouble(o1.getUserRating());
                    double o2Rating = Double.parseDouble(o2.getUserRating());
                    return (o1Rating > o2Rating) ? -1 : 1;
                }
            });

            return jsonParser.writeValueAsString(naverAPIResponseDto);
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
