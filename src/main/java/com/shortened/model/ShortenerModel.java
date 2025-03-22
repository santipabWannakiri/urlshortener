package com.shortened.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "shortened_url")
public class ShortenerModel {

    @Id
    private Long id;
    private String original_url;
    private String shortened_url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginal_url() {
        return original_url;
    }

    public void setOriginal_url(String original_url) {
        this.original_url = original_url;
    }

    public String getShortened_url() {
        return shortened_url;
    }

    public void setShortened_url(String shortened_url) {
        this.shortened_url = shortened_url;
    }

    public ShortenerModel() {
    }

    public ShortenerModel(Long id, String original_url, String shortened_url) {
        this.id = id;
        this.original_url = original_url;
        this.shortened_url = shortened_url;
    }
}
