package com.codeworrisors.Movie_Community_Web.dto;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
public class ErrorResponse {

    private String error;
    private Timestamp timestamp;
    private Enum status;
    private String message;
    private String path;

}
