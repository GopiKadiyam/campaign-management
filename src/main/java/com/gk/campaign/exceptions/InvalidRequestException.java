package com.gk.campaign.exceptions;


import java.util.Map;

public class InvalidRequestException extends RuntimeException {
    Map<String,String> errors;

    public InvalidRequestException(){

    }
    public InvalidRequestException(Map<String, String> errors){
        this.errors=errors;
    }
}
