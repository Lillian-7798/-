package com.example.proj;

import com.example.proj.repository.templateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ServletComponentScan(basePackages = "com.example.proj.interceptor")
public class ProjApplication {


    public static void main(String[] args) {
        SpringApplication.run(ProjApplication.class, args);
        //MongoClient mongoClient = new MongoClient();
    }

}
