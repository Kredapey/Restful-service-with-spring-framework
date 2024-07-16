package aston.bootcamp.config.controller;

import aston.bootcamp.controllers.DealershipController;
import aston.bootcamp.service.DealershipService;
import aston.bootcamp.service.impl.DealershipServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
@Import(DealershipController.class)
public class DealershipControllerTestConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public DealershipService dealershipService() {
        return Mockito.mock(DealershipServiceImpl.class);
    }
}
