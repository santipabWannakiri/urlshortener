package com.shortened.util;

import java.util.concurrent.atomic.AtomicLong;

public class Util {

    private static final AtomicLong safeId = new AtomicLong(1);

    //Alphanumeric 0-9, A-Z. a-z
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static Long generateId() {
        return safeId.getAndIncrement();
    }

    public static String encodeBase62(long id) {
        StringBuilder builder = new StringBuilder();
        while (id > 0) {
            int rem = (int) (id % 62);
            builder.insert(0, BASE62.charAt(rem));
            id /= 62;
        }
        return builder.toString();
    }

    public static long decodeBase62(String shortenedUrl) {
        long id = 0;
        for (char c : shortenedUrl.toCharArray()) {
            id = id * 62 + BASE62.indexOf(c);
        }
        return id;
    }

    public static String getShortenedCode(String fullUrl){
        return fullUrl.substring(fullUrl.lastIndexOf("/")+1);
    }
}
