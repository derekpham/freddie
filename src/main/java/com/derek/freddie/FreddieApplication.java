package com.derek.freddie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories("com.derek.freddie.repository")
public class FreddieApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreddieApplication.class, args);
    }
}
