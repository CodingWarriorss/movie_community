package com.codeworrisors.Movie_Community_Web.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@RestController
@RequestMapping("/test")
@Secured(value = "*")
public class TestController {
    

    @PostMapping
    public String test(){
        return "test";
    }

    @PostMapping("/reqbody")
    public String test1(@RequestBody TestDTO test){
        System.out.println(test.toString());

        return "test1";
    }

    @PostMapping("/model")
    public String test2(@ModelAttribute TestDTO test){
        System.out.println(test.toString());
        return "test2";
    }

    @PostMapping("/reqparam")
    public String test3( @RequestParam("test") String test){
        System.out.println(test.toString());
        return "test3";
    } 
         


}

@Getter
@Setter
@ToString
class TestDTO{
    String test;
    String test2;
    SubTest testDTO;
}

@Getter
@Setter
@ToString
class SubTest{
    String test;
    String test2;


}
