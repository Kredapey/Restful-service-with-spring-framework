package aston.bootcamp.config.service;

import aston.bootcamp.repository.TypeRepository;
import aston.bootcamp.service.impl.TypeServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(TypeServiceImpl.class)
public class TypeServiceTestConfig {
    @Bean
    public TypeRepository typeRepository() {
        return Mockito.mock(TypeRepository.class);
    }
}
