package com.sotero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.sotero")
public class AppNominasSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppNominasSpringApplication.class, args);
    }
}

