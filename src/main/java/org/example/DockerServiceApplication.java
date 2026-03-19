package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = { "org.example.dataaccess"})
@EnableMongoAuditing
@EntityScan(basePackages = {"org.example.dataaccess"})
@SpringBootApplication(scanBasePackages = "org.example", exclude = {DataSourceAutoConfiguration.class})
@Slf4j
public class DockerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DockerServiceApplication.class, args);
    }
}