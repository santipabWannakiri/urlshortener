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
