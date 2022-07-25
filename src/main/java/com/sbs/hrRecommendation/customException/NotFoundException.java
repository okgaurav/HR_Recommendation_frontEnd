package com.sbs.hrRecommendation.customException;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException{

    public String message;
    public String errorStatus;
    public NotFoundException(String message, String errorStatus){
        super();
        this.message = message;
        this.errorStatus = errorStatus;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
}
