package aston.bootcamp.service;

import aston.bootcamp.config.service.BikeServiceTestConfig;
import aston.bootcamp.dto.*;
import aston.bootcamp.exceptions.NotFoundException;
import aston.bootcamp.model.Bike;
import aston.bootcamp.model.Brand;
import aston.bootcamp.model.Type;
import aston.bootcamp.repository.BikeRepository;
import aston.bootcamp.service.impl.BikeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {BikeServiceTestConfig.class})
public class BikeServiceTest {
    @Autowired
    private BikeRepository bikeRepository;
    private static Type type;
    private static Brand brand;
    @InjectMocks
    private BikeServiceImpl bikeService;

    @BeforeAll
    static void beforeAll() {
        type = new Type(1L, "test type", new ArrayList<>());
        brand = new Brand(1L, "test brand", new ArrayList<>());
    }

    @Test
    void save() {
        Long expectedId = 1L;
        BikeIncomingDto bikeIncomingDto = new BikeIncomingDto(type, brand, "test_model", 100L);
        Bike bike = new Bike(expectedId, type, brand, "test_model", 100L, List.of());
        Mockito.doReturn(bike).when(bikeRepository).save(Mockito.any(Bike.class));
        BikeOutgoingDto result = bikeService.createBike(bikeIncomingDto);
        Assertions.assertEquals(expectedId, result.getId());
    }

    @Test
    void update() throws NotFoundException {
        Long expectedId = 1L;
        BikeUpdateDto bikeUpdateDto = new BikeUpdateDto(expectedId, new TypeUpdateDto(1L, type.getType(), new ArrayList<>()),
                new BrandUpdateDto(1L, brand.getBrand(), new ArrayList<>()),
                "test_model",
                100L,
                List.of());
        Mockito.doReturn(true).when(bikeRepository).existsById(Mockito.any());
        bikeService.updateBike(1L, bikeUpdateDto);
        ArgumentCaptor<Bike> argumentCaptor = ArgumentCaptor.forClass(Bike.class);
        Mockito.verify(bikeRepository).save(argumentCaptor.capture());
        Assertions.assertEquals(expectedId, argumentCaptor.getValue().getId());
    }

    @Test
    void updateNotFoundException() {
        BikeUpdateDto bikeUpdateDto = new BikeUpdateDto(1L, null, null, "test_model", 100L, null);
        Mockito.doReturn(false).when(bikeRepository).existsById(Mockito.any());
        NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
                () -> bikeService.updateBike(1L, bikeUpdateDto));
        Assertions.assertEquals("Bike not found", ex.getMessage());
    }

    @Test
    void updateIllegalArgumentException() {
        BikeUpdateDto bikeUpdateDto = new BikeUpdateDto(null, null, null, "test_model", 100L, null);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> bikeService.updateBike(null, bikeUpdateDto));
        Assertions.assertEquals("Incorrect bike params", ex.getMessage());
    }

    @Test
    void delete() throws NotFoundException {
        Long expectedId = 1L;
        Mockito.doReturn(true).when(bikeRepository).existsById(Mockito.any());
        bikeService.deleteByBikeId(expectedId);
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(bikeRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(expectedId, argumentCaptor.getValue());
    }

    @Test
    void findById() throws NotFoundException {
        Long expectedId = 1L;
        Optional<Bike> expectedResult = Optional.of(
                new Bike(expectedId, type, brand, "test_model",
                        100L, List.of()));
        Mockito.doReturn(true).when(bikeRepository).existsById(Mockito.any());
        Mockito.doReturn(expectedResult).when(bikeRepository).findById(Mockito.any(Long.class));
        BikeOutgoingDto bikeOutgoingDto = bikeService.getBikeById(expectedId);
        Assertions.assertEquals(expectedId, bikeOutgoingDto.getId());
    }

    @Test
    void findByIdNotFoundException() {
        Long id = 100L;
        Optional<Bike> nullOptional = Optional.empty();
        Mockito.doReturn(true).when(bikeRepository).existsById(Mockito.any());
        Mockito.doReturn(nullOptional).when(bikeRepository).findById(Mockito.any(Long.class));
        EntityNotFoundException ex = Assertions.assertThrows(EntityNotFoundException.class,
                () -> bikeService.getBikeById(id));
        Assertions.assertEquals("Bike not found", ex.getMessage());
    }

    @Test
    void findAll() {
        List<Bike> expectedResult = List.of(
                new Bike(1L, type, brand,
                        "test_model",
                        100L,
                        List.of()),
                new Bike(2L, type, brand,
                        "test_model",
                        100L,
                        List.of())
        );
        Mockito.doReturn(expectedResult).when(bikeRepository).findAll();
        List<BikeOutgoingDto> result = bikeService.getAllBikes();
        Assertions.assertEquals(expectedResult.size(), result.size());
    }
}
