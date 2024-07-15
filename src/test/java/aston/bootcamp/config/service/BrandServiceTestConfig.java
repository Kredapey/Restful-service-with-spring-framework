package aston.bootcamp.config.service;

import aston.bootcamp.repository.BrandRepository;
import aston.bootcamp.service.impl.BrandServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BrandServiceImpl.class)
public class BrandServiceTestConfig {
    @Bean
    public BrandRepository brandRepository() {
        return Mockito.mock(BrandRepository.class);
    }
}
