package com.shortened.controller;

import com.shortened.model.generic.request.UrlRequest;
import com.shortened.model.generic.response.GenericResponse;
import com.shortened.service.ShortenerService;
import com.shortened.util.ResponseUtilities;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class ShortenedUrlController {
    @Autowired
    private ShortenerService shortenerService;

    public ShortenedUrlController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @GetMapping("/health")
    public ResponseEntity<GenericResponse> healthCheck() {
        return ResponseUtilities.createSuccessResponse("The system status is : OK");
    }

    @PostMapping("/shortened")
    public ResponseEntity<GenericResponse> generateShortenedUrl(@Valid @RequestBody UrlRequest urlRequest) {
        String shortenedUrl = shortenerService.generateShortenedUrl(urlRequest.getUrl());
        return ResponseUtilities.createSuccessResponse("Shortened URL : " + shortenedUrl);
    }

    @PostMapping("/original")
    public ResponseEntity<GenericResponse> queryOriginalUrl(@Valid @RequestBody UrlRequest urlRequest) {
        String originalUrl = shortenerService.decodeShortenedUrl(urlRequest.getUrl());
        return ResponseUtilities.createSuccessResponse("Original URL : " + originalUrl);
    }

}
