package com.amalitech.springbatchprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringBatchProcessingApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringBatchProcessingApplication.class, args);
    }
    
}
