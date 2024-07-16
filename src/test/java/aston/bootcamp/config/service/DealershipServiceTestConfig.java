package aston.bootcamp.config.service;

import aston.bootcamp.repository.BikeRepository;
import aston.bootcamp.repository.DealershipRepository;
import aston.bootcamp.service.impl.DealershipServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DealershipServiceImpl.class)
public class DealershipServiceTestConfig {
    @Bean
    public DealershipRepository dealershipRepository() {
        return Mockito.mock(DealershipRepository.class);
    }

    @Bean
    public BikeRepository bikeRepository() {
        return Mockito.mock(BikeRepository.class);
    }
}
