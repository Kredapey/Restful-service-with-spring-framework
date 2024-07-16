package aston.bootcamp.config.controller;

import aston.bootcamp.controllers.TypeController;
import aston.bootcamp.service.TypeService;
import aston.bootcamp.service.impl.TypeServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@Import(TypeController.class)
public class TypeControllerTestConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public TypeService typeService() {
        return Mockito.mock(TypeServiceImpl.class);
    }
}

