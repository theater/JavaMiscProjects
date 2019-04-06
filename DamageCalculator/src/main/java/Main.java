package main.java;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class Main {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext ctxt = SpringApplication.run(Main.class, args);
    }
}