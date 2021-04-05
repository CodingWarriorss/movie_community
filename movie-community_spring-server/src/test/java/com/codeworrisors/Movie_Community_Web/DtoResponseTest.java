package com.codeworrisors.Movie_Community_Web;

import com.codeworrisors.Movie_Community_Web.dto.BoxOfficeRankingDto;
import com.codeworrisors.Movie_Community_Web.model.BoxOfficeRanking;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@WebMvcTest
@Controller
public class DtoResponseTest {

    @RequestMapping("/rresponse/test")
    public BoxOfficeRankingDto test(){
        BoxOfficeRankingDto testDto = new BoxOfficeRankingDto();

        testDto.setRank(34);
        testDto.setMovieNm("tse");
        testDto.setRankInten("test");
        return testDto;
    }
}
