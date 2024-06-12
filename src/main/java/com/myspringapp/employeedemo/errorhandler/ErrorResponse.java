package com.myspringapp.employeedemo.errorhandler;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse{
    private int  statusCode;
    private String message;

    public ErrorResponse (int  statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
