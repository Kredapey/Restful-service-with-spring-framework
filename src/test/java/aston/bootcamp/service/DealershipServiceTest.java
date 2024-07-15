package aston.bootcamp.service;

import aston.bootcamp.config.service.BikeServiceTestConfig;
import aston.bootcamp.config.service.DealershipServiceTestConfig;
import aston.bootcamp.dto.DealershipIncomingDto;
import aston.bootcamp.dto.DealershipOutgoingDto;
import aston.bootcamp.dto.DealershipUpdateDto;
import aston.bootcamp.exceptions.NotFoundException;
import aston.bootcamp.model.Dealership;
import aston.bootcamp.repository.BikeRepository;
import aston.bootcamp.repository.DealershipRepository;
import aston.bootcamp.service.impl.DealershipServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {DealershipServiceTestConfig.class})
public class DealershipServiceTest {
    @Autowired
    DealershipRepository dealershipRepository;
    @Autowired
    BikeRepository bikeRepository;
    @InjectMocks
    DealershipServiceImpl dealershipService;

    @Test
    void save() {
        Long expectedId = 1L;
        DealershipIncomingDto dealershipIncomingDto = new DealershipIncomingDto("city", "street", 10L);
        Dealership dealership = new Dealership(expectedId, "city", "street", 10L, List.of());
        Mockito.doReturn(dealership).when(dealershipRepository).save(Mockito.any(Dealership.class));
        DealershipOutgoingDto result = dealershipService.createDealership(dealershipIncomingDto);
        Assertions.assertEquals(expectedId, result.getId());
    }

    @Test
    void update() throws NotFoundException {
        Long expectedId = 1L;
        DealershipUpdateDto dealershipUpdateDto = new DealershipUpdateDto(expectedId, "city", "street", 10L, List.of());
        Mockito.doReturn(true).when(dealershipRepository).existsById(Mockito.any());
        dealershipService.updateDealership(expectedId, dealershipUpdateDto);
        ArgumentCaptor<Dealership> argumentCaptor = ArgumentCaptor.forClass(Dealership.class);
        Mockito.verify(dealershipRepository).save(argumentCaptor.capture());
        Assertions.assertEquals(expectedId, argumentCaptor.getValue().getId());
    }

    @Test
    void updateNotFoundException() {
        DealershipUpdateDto dealershipUpdateDto = new DealershipUpdateDto(1L, "city", "street", 10L, List.of());
        Mockito.doReturn(false).when(dealershipRepository).existsById(Mockito.any());
        NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
                () -> dealershipService.updateDealership(1L, dealershipUpdateDto));
        Assertions.assertEquals("Dealership not found", ex.getMessage());
    }

    @Test
    void updateIllegalArgumentException() {
        DealershipUpdateDto dealershipUpdateDto = new DealershipUpdateDto(null, "city", "street", 10L, List.of());
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> dealershipService.updateDealership(null, dealershipUpdateDto));
        Assertions.assertEquals("Incorrect dealership params", ex.getMessage());
    }

    @Test
    void delete() throws NotFoundException {
        Long expectedId = 1L;
        Mockito.doReturn(true).when(dealershipRepository).existsById(Mockito.any());
        dealershipService.deleteDealershipById(expectedId);
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(dealershipRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(expectedId, argumentCaptor.getValue());
    }

    @Test
    void findById() throws NotFoundException {
        Long expectedId = 1L;
        Optional<Dealership> expectedResult = Optional.of(
                new Dealership(expectedId, "city", "street", 10L, List.of())
        );
        Mockito.doReturn(true).when(dealershipRepository).existsById(Mockito.any());
        Mockito.doReturn(expectedResult).when(dealershipRepository).findById(Mockito.any(Long.class));
        DealershipOutgoingDto typeOutgoingDto = dealershipService.getDealershipById(expectedId);
        Assertions.assertEquals(expectedId, typeOutgoingDto.getId());
    }

    @Test
    void findByIdNotFoundException() {
        Long id = 100L;
        Optional<Dealership> nullOptional = Optional.empty();
        Mockito.doReturn(true).when(dealershipRepository).existsById(Mockito.any());
        Mockito.doReturn(nullOptional).when(dealershipRepository).findById(Mockito.any(Long.class));
        EntityNotFoundException ex = Assertions.assertThrows(EntityNotFoundException.class,
                () -> dealershipService.getDealershipById(id));
        Assertions.assertEquals("Dealership not found", ex.getMessage());
    }

    @Test
    void findAll() {
        List<Dealership> expectedResult = List.of(
                new Dealership(1L, "city", "street", 10L, List.of()),
                new Dealership(2L, "city", "street", 10L, List.of())
        );
        Mockito.doReturn(expectedResult).when(dealershipRepository).findAll();
        List<DealershipOutgoingDto> result = dealershipService.getAllDealerships();
        Assertions.assertEquals(expectedResult.size(), result.size());
    }



}
