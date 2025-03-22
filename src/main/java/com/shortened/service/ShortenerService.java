package com.shortened.service;

import com.shortened.model.ShortenerModel;
import com.shortened.repository.ShortenerRepository;
import com.shortened.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ShortenerService {

    private ShortenerRepository repository;

    @Autowired
    public ShortenerService(ShortenerRepository repository) {
        this.repository = repository;
    }

    public String generateShortenedUrl(String originalUrl) {
        Long id = Util.generateId();
        String shortenedUrl = Util.encodeBase62(id);
        ShortenerModel model = new ShortenerModel(id, originalUrl, shortenedUrl);
        model = repository.save(model);
        return model.getShortened_url();
    }

    public String decodeShortenedUrl(String shortenedUrl) {
        String shortenedCode = Util.getShortenedCode(shortenedUrl);
        Long id = Util.decodeBase62(shortenedCode);
        Optional<ShortenerModel> model = repository.findById(id);
        return model.get().getOriginal_url();
    }
}
