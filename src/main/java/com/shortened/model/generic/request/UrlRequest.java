package com.shortened.model.generic.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UrlRequest {

    @NotBlank(message = "is mandatory")
    @Size(min = 10, max = 200, message = "originalURL must be between 10 and 200 characters")
    private String url;


    public UrlRequest() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
