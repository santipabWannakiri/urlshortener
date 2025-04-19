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


## Prometheus & Grafana
1. **Add actuator and  micrometer-registry-prometheus dependency** in `pom.xml`
2. **Configure endpoint connection** in `application.properties` 
 ```cli
management.endpoints.web.exposure.include=prometheus,health,info
management.endpoint.prometheus.enabled=true
management.metrics.export.prometheus.enabled=true
 ```
3. **Create Docker Compose for Prometheus & Grafana**
```cli
version: '3'

services:
  prometheus:
    image: prom/prometheus
    ports:
      - "9090:9090"
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml

  grafana:
    image: grafana/grafana
    ports:
      - "3000:3000"
    volumes:
      - grafana-storage:/var/lib/grafana

volumes:
  grafana-storage:

```

3. **Prometheus Config (prometheus.yml)**
```cli
global:
  scrape_interval: 5s

scrape_configs:
  - job_name: 'springboot-app'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['host.docker.internal:8080']  # Use localhost or IP for Linux

```

## 🔍 Useful Endpoints (Spring Boot Actuator)

These endpoints are exposed via Spring Boot Actuator and are used for monitoring and Prometheus scraping:

| Endpoint                        | Description                              | Example URL                              |
|---------------------------------|------------------------------------------|-------------------------------------------|
| `/actuator/health`             | Basic health check of the app            | http://localhost:8080/actuator/health     |
| `/actuator/info`               | Custom build/application info            | http://localhost:8080/actuator/info       |
| `/actuator/prometheus`         | Prometheus metrics scrape endpoint       | http://localhost:8080/actuator/prometheus |
| `/actuator`                    | Shows available actuator endpoints       | http://localhost:8080/actuator            |

> ℹ️ These endpoints must be enabled in `application.properties`:

### 🔍 Access Prometheus UI

- Open in browser:  
  **http://localhost:9090**

  
## 📈 Grafana Setup & Dashboard Import

Once Prometheus and Grafana are up and running:

### 🧭 Access Grafana

- Open Grafana in your browser:  
  **http://localhost:3000**

- Default login credentials:  
  - **Username:** `admin`  
  - **Password:** `admin`  
  *(You will be prompted to change the password on first login)*

---

### 🔌 Add Prometheus as a Data Source

1. Go to **⚙️Data Sources**
2. Click **"Add data source"**
3. Choose **Prometheus**
4. Set the URL: **http://host.docker.internal:9090**
5. Click **"Save & Test"**

### 📊 Import a Prebuilt Dashboard
1. Go to **Dashboards → New → import →  URL or ID dashboard → Load**
2. Search Spring boot dashboard at **https://grafana.com/grafana/dashboards/**
3. Choose data source and import

📊 Example: Custom Dashboard for HTTP Status Code Count
1. Go to **⚙️Dashboard → Edit → Add → Visualization**
2. Select data soruce
3. Select metrics **http_server_requests_seconds_count**
4. Label filters : **status**
5. Test **Run queries**
6. Chage label for easy view click **Options**
7. Legend : **custom** → provide **{{status}}**
8. Test **Run queries**
9. Save dashboard
