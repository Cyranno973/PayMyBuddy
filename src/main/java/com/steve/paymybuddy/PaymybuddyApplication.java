package com.steve.paymybuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PaymybuddyApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PaymybuddyApplication.class, args);
    }
}
