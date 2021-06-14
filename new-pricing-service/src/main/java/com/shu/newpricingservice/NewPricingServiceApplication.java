package com.shu.newpricingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class NewPricingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewPricingServiceApplication.class, args);
    }

}
