package com.niek125.rolemanagerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RoleManagerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoleManagerServiceApplication.class, args);
    }

}
