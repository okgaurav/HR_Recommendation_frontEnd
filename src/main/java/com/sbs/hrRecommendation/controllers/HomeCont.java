package com.sbs.hrRecommendation.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeCont {
    @RequestMapping("/")
    public  String home(){
        return "Hello World";
    }
}
