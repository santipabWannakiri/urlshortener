package com.shortened.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "shortened_url")
public class ShortenerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String original_url;
    private String shortened_url;

    public ShortenerModel(Long id, String original_url, String shortened_url) {
        this.id = id;
        this.original_url = original_url;
        this.shortened_url = shortened_url;
    }
}
