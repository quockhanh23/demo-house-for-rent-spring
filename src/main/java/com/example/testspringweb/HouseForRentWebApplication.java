package com.example.testspringweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HouseForRentWebApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(HouseForRentWebApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
