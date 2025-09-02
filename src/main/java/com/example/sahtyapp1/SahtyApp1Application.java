package com.example.sahtyapp1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@SpringBootApplication
public class SahtyApp1Application {

    public static void main(String[] args) {
        SpringApplication.run(SahtyApp1Application.class, args);
    }

}


