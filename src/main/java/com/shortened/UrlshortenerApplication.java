package com.shortened;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlshortenerApplication {
	private static final Logger logger = LogManager.getLogger(UrlshortenerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UrlshortenerApplication.class, args);
		logger.info("Application started successfully.");
	}

}
