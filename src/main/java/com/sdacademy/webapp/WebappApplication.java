package com.sdacademy.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebappApplication.class, args);
    }
    //jdbc:h2:mem:testdb
    //http://localhost:8089/messages/temat1/wiadomosc1_wiadomosc2_wiadomosc3
    //http://localhost:8089/messages/remove/temat2
}

