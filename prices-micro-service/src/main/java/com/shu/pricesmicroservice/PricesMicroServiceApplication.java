package com.shu.pricesmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PricesMicroServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricesMicroServiceApplication.class, args);
    }

}
