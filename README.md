# urlshortener


## Log4j2 Configuration in Spring Boot
1. Exclude Logback
 ```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
    <scope>provided</scope>
</dependency>
 ```

2. Add Log4j2 dependencies
 ```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>${log4j.version}</version>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>${log4j.version}</version>
</dependency>
 ```
3. Create log4j2.xml

## GlobalException Handler for Spring boot Validation
 ```java
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        GenericResponse errorJsonResponse = new GenericResponse();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        // Combine all validation messages into one string (optional)
        String validationErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));

        errorJsonResponse.setAppResponseCode(MessageConstants.INVALID_FORMAT_ERROR_CODE);
        errorJsonResponse.setAppMessageCode(MessageConstants.INVALID_FORMAT_MESSAGE_CODE);
        errorJsonResponse.setDescription(validationErrors);

        return new ResponseEntity<>(errorJsonResponse, httpStatus);
    }
 ```

## Redis cache

### Steps to Configure Redis in Spring Boot:

1. **Add Redis dependency** in `pom.xml` or `build.gradle`.
   
2. **Configure Redis connection** in `application.properties` or `application.yml`.

3. **Enable caching** with `@EnableCaching`.

4. **(Optional)** Define `RedisTemplate` for custom operations.

5. Use `@Cacheable`, `@CachePut`, `@CacheEvict` in your services for caching.

6. **(Optional)** Set up TTL or custom cache configurations via `CacheManager`.

7. Test Redis functionality through `redis-cli` or logs.
 ```cli
docker exec -it redis redis-cli -a mysecretpassword
127.0.0.1:6379> PING
127.0.0.1:6379> GET users:1
127.0.0.1:6379> TTL users:1  //Test Time-to-Live (TTL) in Redis

KEYS *
KEYS shortened_urls:*
GET shortened_urls:abc123
EXISTS shortened_urls:abc123
TTL shortened_urls:abc123
TYPE shortened_urls:abc123
 ```


Cache strategies 
| Annotation    | Strategy           | When used                          |
|---------------|--------------------|-------------------------------------|
| `@Cacheable`  | **Read-through**   | Read once and reuse from cache      |
| `@CachePut`   | **Write-through**  | Always update cache with new data   |
| `@CacheEvict` | **Eviction**       | Clear outdated cache after changes  |

