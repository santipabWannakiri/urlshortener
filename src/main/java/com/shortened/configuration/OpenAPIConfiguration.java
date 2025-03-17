package com.shortened.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Configuration
@Scope("singleton")
public class OpenAPIConfiguration {
    @Value("${factoring.openapi.api.title}")
    private String title;

    @Value("${factoring.openapi.api.version}")
    private String apiVersion;

    @Value("${factoring.openapi.api.description}")
    private String description;

    @Value("${factoring.openapi.url.dev}")
    private String devUrl;

    @Value("${factoring.openapi.url.prod}")
    private String prodUrl;

    public OpenAPI OpenAPIConfig(){
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("This endpoint using for Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("This endpoint using for Production environment");

        Contact contact = new Contact();
        contact.setEmail("shortenedurl@system.com");
        contact.setName("shortened url");
        contact.setUrl("https://www.shortenedurl.com");

        License license = new License().name("shortenedurl.").url("https://shortenedurl/terms");

        Info info = new Info()
                .title(title)
                .version(apiVersion)
                .contact(contact)
                .description(description)
                .termsOfService("https://shortenedurl.com/terms")
                .license(license);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
