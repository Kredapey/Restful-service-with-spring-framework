package aston.bootcamp.config.controller;

import aston.bootcamp.controllers.BikeController;
import aston.bootcamp.service.BikeService;
import aston.bootcamp.service.impl.BikeServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@Import(BikeController.class)
public class BikeControllerTestConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public BikeService bikeService() {
        return Mockito.mock(BikeServiceImpl.class);
    }
}
