package com.shortened.util;

import com.shortened.exception.type.InvalidFormatException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicLong;

public class Util {
    private static final Logger logger = LogManager.getLogger(Util.class);
    private static final AtomicLong safeId = new AtomicLong(1);

    // Alphanumeric 0-9, A-Z, a-z
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static Long generateId() {
        long id = safeId.getAndIncrement();
        logger.info("Generated ID: {}", id);
        return id;
    }

    public static String encodeBase62(long id) {
        if (id < 0) {
            logger.error("Negative ID cannot be encoded: {}", id);
            throw new InvalidFormatException("ID must be non-negative");
        }
        logger.info("Encoding ID to Base62: {}", id);
        StringBuilder builder = new StringBuilder();
        while (id > 0) {
            int rem = (int) (id % 62);
            builder.insert(0, BASE62.charAt(rem));
            id /= 62;
        }
        String encoded = builder.length() > 0 ? builder.toString() : "0";
        logger.info("Encoded Base62: {}", encoded);
        return encoded;
    }

    public static long decodeBase62(String shortenedUrl) {
        if (shortenedUrl == null || shortenedUrl.isEmpty()) {
            logger.error("Cannot decode empty Base62 string.");
            throw new InvalidFormatException("Input cannot be null or empty");
        }
        logger.info("Decoding Base62 string: {}", shortenedUrl);
        long id = 0;
        for (char c : shortenedUrl.toCharArray()) {
            int index = BASE62.indexOf(c);
            if (index == -1) {
                logger.error("Invalid Base62 character found: {}", c);
                throw new InvalidFormatException("Invalid Base62 character: " + c);
            }
            id = id * 62 + index;
        }
        logger.info("Decoded ID: {}", id);
        return id;
    }

    public static String getShortenedCode(String fullUrl) {
        if (fullUrl == null || !fullUrl.contains("/")) {
            logger.error("Invalid URL format: {}", fullUrl);
            throw new InvalidFormatException("Invalid URL format");
        }
        logger.info("Extracting shortened code from URL: {}", fullUrl);
        String code = fullUrl.substring(fullUrl.lastIndexOf("/") + 1);
        if (code.isEmpty()) {
            logger.error("No shortened code found in URL: {}", fullUrl);
            throw new InvalidFormatException("No shortened code found in URL");
        }
        logger.info("Extracted shortened code: {}", code);
        return code;
    }
}