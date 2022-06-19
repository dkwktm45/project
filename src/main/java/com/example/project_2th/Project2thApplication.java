package com.example.project_2th;

import com.example.project_2th.config.TestDatasourceConfig;
import com.example.project_2th.controller.AdminController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class Project2thApplication {

    public static void main(String[] args) {
        SpringApplication.run(Project2thApplication.class, args);
    }

}
