package com.shortened.service;

import com.shortened.model.ShortenerModel;
import com.shortened.repository.ShortenerRepository;
import com.shortened.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
public class ShortenerService {
    private static final Logger logger = LogManager.getLogger(ShortenerService.class);
    private static final String SHORTENED_URL_CACHE = "shortened_urls:";
    private static final String ORIGINAL_URL_CACHE = "original_urls:";
    private ShortenerRepository repository;
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    public ShortenerService(ShortenerRepository repository, RedisTemplate<String, Object> redisTemplate) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
    }

    public String generateShortenedUrl(String originalUrl) {
        logger.info("Generating shortened URL for: {}", originalUrl);
        Long id = Util.generateId();
        String shortenedUrl = Util.encodeBase62(id);
        ShortenerModel model = new ShortenerModel(id, originalUrl, shortenedUrl);
        model = repository.save(model);

        redisTemplate.opsForValue().set(SHORTENED_URL_CACHE+shortenedUrl,originalUrl,40, TimeUnit.SECONDS); //"shortened_urls:abc123"  set key expire in 30 sec
        logger.info("Shortened URL created: {} -> {}", originalUrl, shortenedUrl);
        return model.getShortened_url();
    }

    public String decodeShortenedUrl(String shortenedUrl) {
        logger.info("Decoding shortened URL: {}", shortenedUrl);
        try {
            String shortenedCode = Util.getShortenedCode(shortenedUrl);

            //Check cache first
            String cachedOriginalUrl = (String) redisTemplate.opsForValue().get(SHORTENED_URL_CACHE+shortenedCode);
            if(cachedOriginalUrl != null){
                logger.info("Cache hit for {}: {}", shortenedUrl, cachedOriginalUrl);
                return cachedOriginalUrl;
            }

            Long id = Util.decodeBase62(shortenedCode);
            Optional<ShortenerModel> model = repository.findById(id);
            if (model.isPresent()) {
                logger.info("Original URL found for {}: {}", shortenedUrl, model.get().getOriginal_url());
                return model.get().getOriginal_url();
            } else {
                logger.warn("No mapping found for shortened URL: {}", shortenedUrl);
                throw new IllegalArgumentException("Shortened URL not found.");
            }
        } catch (IllegalArgumentException e) {
            logger.error("Error decoding shortened URL: {}", shortenedUrl, e);
            throw e;
        }
    }
}
