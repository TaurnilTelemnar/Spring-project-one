package com.javarush.projectspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@PropertySource("classpath:application.properties")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
