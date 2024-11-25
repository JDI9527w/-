package com.quanyou.quanyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuanyouApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuanyouApplication.class, args);
    }

}
