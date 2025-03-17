package com.shortened.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class ShortenedUrlController {

    @GetMapping("/health")
    public String healthCheck(){
        return "The system status is : OK";
    }
}
