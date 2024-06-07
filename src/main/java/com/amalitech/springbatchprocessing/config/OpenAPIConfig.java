package com.amalitech.springbatchprocessing.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(contact = @Contact(name = "Richard Bouaro", email = "richard.bouaro@amalitech.com", url = "https://amalitech.org"),
        
        description = "OpenAPI documentation for Spring Batch Processing API", title = "Spring Batch Processing API",
        version = "1.0.0", license = @License(name = "AMALITECH", url = "https://amalitech.org")

), servers = {@Server(description = "Development Server", url = "http://localhost:8600"),
        @Server(description = "Production Server", url = "http://localhost:8600")
    
})

public class OpenAPIConfig {

}
