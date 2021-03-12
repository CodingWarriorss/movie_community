package com.codeworrisors.Movie_Community_Web.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ApiResponse {

    private boolean success;
    private String message;
    
}
