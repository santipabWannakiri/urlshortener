package com.shortened.controller;

import com.shortened.model.generic.response.GenericResponse;
import com.shortened.util.ResponseUtilities;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class ShortenedUrlController {

    @GetMapping("/health")
    public ResponseEntity<GenericResponse> healthCheck(){
        return ResponseUtilities.createSuccessResponse("The system status is : OK");
    }
}
