package com.codeworrisors.Movie_Community_Web;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class JacksonTest {

    @Test
    public void test(){
//        File file = new File("C:/workspace/gitRepository/movie_community/movie-community_react-app/src/test_data/review_test_data.json");

        ObjectMapper objectMapper = new ObjectMapper();

        String testInput = "{\"reviewId\":\" 123\",\"writerName\":\"test name\",\"createData\":\"test\",\"thumnailUri\":null, \"noProperty\":\"what\"}";
        try {
            Reviews reviews = objectMapper.readValue(testInput, Reviews.class);

            if( reviews.thumnailUri == null) System.out.println("that's null.");
            System.out.println(reviews);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
class Reviews implements Serializable{
    public String reviewId;
    public String writerName;
    public String createData;
    public String thumnailUri;

    @Override
    public String toString() {
        return "Reviews{" +
                "reviewId='" + reviewId + '\'' +
                ", writerName='" + writerName + '\'' +
                ", createData='" + createData + '\'' +
                ", thumnailUri='" + thumnailUri + '\'' +
                '}';
    }
}



class MyItem {
    public String itemName;
    public int itemPrice;

    @Override
    public String toString() {
        return "MyItem{" +
                "itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                '}';
    }
}