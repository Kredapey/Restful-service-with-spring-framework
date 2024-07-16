package aston.bootcamp.config.controller;

import aston.bootcamp.controllers.BrandController;
import aston.bootcamp.service.BrandService;
import aston.bootcamp.service.impl.BrandServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@Import(BrandController.class)
public class BrandControllerTestConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public BrandService bikeService() {
        return Mockito.mock(BrandServiceImpl.class);
    }
}
