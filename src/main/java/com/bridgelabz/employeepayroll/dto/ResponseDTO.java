package com.bridgelabz.employeepayroll.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDTO {
    private String message;
    private Object data;
    private int statusCode;

    // Constructors
//    public ResponseDTO(){}
//

    public ResponseDTO(String message, HttpStatus statusCode, Object data) {
        this.message = message;
        this.statusCode = statusCode.value();
        this.data = data;
    }
//
//    // Getters and Setters
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public Object getData() {
//        return data;
//    }
//
//    public void setData(Object data) {
//        this.data = data;
//    }
//
//    public int getStatusCode() {
//        return statusCode;
//    }
//
//    public void setStatusCode(HttpStatus statusCode) {
//        this.statusCode = statusCode.value();
//    }


}

