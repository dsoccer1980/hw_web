package ru.dsoccer1980;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
@EnableHystrix
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
