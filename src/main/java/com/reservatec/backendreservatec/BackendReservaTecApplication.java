package com.reservatec.backendreservatec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendReservaTecApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendReservaTecApplication.class, args);
    }

}
