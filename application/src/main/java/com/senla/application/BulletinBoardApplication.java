package com.senla.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.senla"})
public class BulletinBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BulletinBoardApplication.class, args);
    }

}
