package com.bridgelabz.employeepayroll.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseDTO {
    private String message;
    private Object data;
    private int statusCode;

    public ResponseDTO(String message, HttpStatus statusCode, Object data) {
        this.message = message;
        this.statusCode = statusCode.value();
        this.data = data;
    }

}

