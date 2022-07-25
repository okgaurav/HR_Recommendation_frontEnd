package com.sbs.hrRecommendation.globalException;

import com.sbs.hrRecommendation.customException.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.NoSuchElementException;

@ControllerAdvice
public class Global {

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<String> handleNotFoundException(NotFoundException nfe){
//        return new ResponseEntity<String>(nfe.getMessage(), nfe.excStatus());
//    }
}
