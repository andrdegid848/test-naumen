package com.example.naumen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class NaumenApplication {

    public static void main(String[] args) {
        SpringApplication.run(NaumenApplication.class, args);
    }

}
