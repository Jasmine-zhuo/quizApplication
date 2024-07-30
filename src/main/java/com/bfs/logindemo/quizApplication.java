package com.bfs.logindemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

// Entry point of the application.
@SpringBootApplication
@ServletComponentScan
public class quizApplication {

    public static void main(String[] args) {
        SpringApplication.run(quizApplication.class, args);
    }

}