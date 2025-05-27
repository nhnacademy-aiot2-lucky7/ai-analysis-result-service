package com.nhnacademy.ai_analysis_result_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AiAnalysisResultServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiAnalysisResultServiceApplication.class, args);
    }

}
