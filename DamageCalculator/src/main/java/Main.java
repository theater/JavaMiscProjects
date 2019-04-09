package main.java;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import main.java.web.Calculate;


@SpringBootApplication
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Calculate.class);

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Main.class, args);
        logger.info("Usage: open http://localhost:8080 in your browser");
    }
}