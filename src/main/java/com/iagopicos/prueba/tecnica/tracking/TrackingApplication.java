package com.iagopicos.prueba.tecnica.tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class TrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackingApplication.class, args);
    }

}
