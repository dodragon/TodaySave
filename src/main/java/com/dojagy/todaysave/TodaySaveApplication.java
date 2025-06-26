package com.dojagy.todaysave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TodaySaveApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodaySaveApplication.class, args);
    }

}
