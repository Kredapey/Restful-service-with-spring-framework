package aston.bootcamp.config.service;

import aston.bootcamp.repository.BikeRepository;
import aston.bootcamp.service.impl.BikeServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BikeServiceImpl.class)
public class BikeServiceTestConfig {
    @Bean
    public BikeRepository bikeRepository() {
        return Mockito.mock(BikeRepository.class);
    }

}
