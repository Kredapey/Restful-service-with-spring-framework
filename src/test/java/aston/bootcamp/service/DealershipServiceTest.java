package aston.bootcamp.service;

import aston.bootcamp.config.service.DealershipServiceTestConfig;
import aston.bootcamp.dto.DealershipIncomingDto;
import aston.bootcamp.dto.DealershipOutgoingDto;
import aston.bootcamp.dto.DealershipUpdateDto;
import aston.bootcamp.dto.mapper.DealershipDtoMapper;
import aston.bootcamp.dto.mapper.DealershipDtoMapperImpl;
import aston.bootcamp.exceptions.NotFoundException;
import aston.bootcamp.model.Bike;
import aston.bootcamp.model.Brand;
import aston.bootcamp.model.Dealership;
import aston.bootcamp.model.Type;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {DealershipServiceTestConfig.class})
public class DealershipServiceTest {
    @Autowired
    private DealershipRepository dealershipRepository;
    @Autowired
    private BikeRepository bikeRepository;
    @InjectMocks
    private DealershipServiceImpl dealershipService;
    private static final DealershipDtoMapper MAPPER = new DealershipDtoMapperImpl();

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
        Mockito.doReturn(new Dealership(dealershipUpdateDto.getId(),
                dealershipUpdateDto.getCity(),
                dealershipUpdateDto.getStreet(),
                dealershipUpdateDto.getHouseNum(),
                List.of())).when(dealershipRepository).save(Mockito.any());
        DealershipOutgoingDto myDealership = dealershipService.updateDealership(expectedId, dealershipUpdateDto);
        Assertions.assertEquals(expectedId, myDealership.getId());
        Assertions.assertEquals(dealershipUpdateDto.getCity(), myDealership.getCity());
        Assertions.assertEquals(dealershipUpdateDto.getStreet(), myDealership.getStreet());
        Assertions.assertEquals(dealershipUpdateDto.getHouseNum(), myDealership.getHouseNum());
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

    @Test
    void addBikeToDealership() throws NotFoundException {
        Long bikeId = 1L;
        Long dealershipId = 1L;
        Dealership dealership = new Dealership(1L, "Test", "test", 1L, new ArrayList<>());
        Bike bike = new Bike(1L, new Type(1L,
                "test", new ArrayList<>()),
                new Brand(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>());
        Mockito.doReturn(true).when(dealershipRepository).existsById(Mockito.any());
        Mockito.doReturn(true).when(bikeRepository).existsById(Mockito.any());
        Mockito.doReturn(Optional.of(dealership)).when(dealershipRepository).findById(Mockito.anyLong());
        Mockito.doReturn(Optional.of(bike)).when(bikeRepository).findById(Mockito.anyLong());
        Dealership expectedDealership = new Dealership(dealership.getId(), dealership.getCity(), dealership.getStreet(), dealership.getHouseNum(), List.of(bike));
        Mockito.doReturn(dealership).when(dealershipRepository).save(Mockito.any());
        DealershipOutgoingDto expectedDealershipOutgoingDto = MAPPER.dealershipToDealershipOutgoingDto(expectedDealership);
        DealershipOutgoingDto myDealershipOutgoingDto = dealershipService.addBikeToDealership(dealershipId, bikeId);
        Assertions.assertEquals(expectedDealershipOutgoingDto.getId(), myDealershipOutgoingDto.getId());
        Assertions.assertEquals(expectedDealershipOutgoingDto.getStreet(), myDealershipOutgoingDto.getStreet());
        Assertions.assertEquals(expectedDealershipOutgoingDto.getCity(), myDealershipOutgoingDto.getCity());
        Assertions.assertEquals(expectedDealershipOutgoingDto.getHouseNum(), myDealershipOutgoingDto.getHouseNum());
        Assertions.assertEquals(expectedDealershipOutgoingDto.getBikes().size(), myDealershipOutgoingDto.getBikes().size());
        Assertions.assertEquals(expectedDealershipOutgoingDto.getBikes().get(0), myDealershipOutgoingDto.getBikes().get(0));
    }

    @Test
    void addBikeToDealershipNoDealership() {
        Mockito.doReturn(false).when(dealershipRepository).existsById(Mockito.any());
        NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
                () -> dealershipService.addBikeToDealership(1L, 1L));
        Assertions.assertEquals("Dealership not found", ex.getMessage());
    }

    @Test
    void addBikeToDealershipNoBike() {
        Mockito.doReturn(true).when(dealershipRepository).existsById(Mockito.any());
        Mockito.doReturn(false).when(bikeRepository).existsById(Mockito.any());
        NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
                () -> dealershipService.addBikeToDealership(1L, 1L));
        Assertions.assertEquals("Bike not found", ex.getMessage());
    }

    @Test
    void addBikeToDealershipEmptyDealership() {
        Mockito.doReturn(true).when(dealershipRepository).existsById(Mockito.any());
        Mockito.doReturn(true).when(bikeRepository).existsById(Mockito.any());
        Mockito.doReturn(Optional.empty()).when(dealershipRepository).findById(Mockito.anyLong());
        EntityNotFoundException ex = Assertions.assertThrows(EntityNotFoundException.class,
                () -> dealershipService.addBikeToDealership(1L, 1L));
        Assertions.assertEquals("Dealership not found", ex.getMessage());
    }

    @Test
    void addBikeToDealershipEmptyBike() {
        Mockito.doReturn(true).when(dealershipRepository).existsById(Mockito.any());
        Mockito.doReturn(true).when(bikeRepository).existsById(Mockito.any());
        Mockito.doReturn(Optional.of(new Dealership())).when(dealershipRepository).findById(Mockito.anyLong());
        Mockito.doReturn(Optional.empty()).when(bikeRepository).findById(Mockito.any());
        EntityNotFoundException ex = Assertions.assertThrows(EntityNotFoundException.class,
                () -> dealershipService.addBikeToDealership(1L, 1L));
        Assertions.assertEquals("Bike not found", ex.getMessage());
    }

    @Test
    void deleteBikeFromDealership() throws NotFoundException {
        Long bikeId = 1L;
        Long dealershipId = 1L;
        Dealership dealership = new Dealership(1L, "Test", "test", 1L, new ArrayList<>());
        Bike bike = new Bike(1L, new Type(1L,
                "test", new ArrayList<>()),
                new Brand(1L, "test", new ArrayList<>()),
                "test",
                100L,
                List.of(dealership));
        List<Bike> list = List.of(bike);
        dealership.setBikes(list);
        Mockito.doReturn(true).when(dealershipRepository).existsById(Mockito.any());
        Mockito.doReturn(true).when(bikeRepository).existsById(Mockito.any());
        Mockito.doReturn(Optional.of(dealership)).when(dealershipRepository).findById(Mockito.anyLong());
        Mockito.doReturn(Optional.of(bike)).when(bikeRepository).findById(Mockito.anyLong());
        Dealership expectedDealership = new Dealership(dealership.getId(), dealership.getCity(), dealership.getStreet(), dealership.getHouseNum(), List.of());
        Mockito.doReturn(dealership).when(dealershipRepository).save(Mockito.any());
        DealershipOutgoingDto expectedDealershipOutgoingDto = MAPPER.dealershipToDealershipOutgoingDto(expectedDealership);
        DealershipOutgoingDto myDealershipOutgoingDto = dealershipService.deleteBikeFromDealership(dealershipId, bikeId);
        Assertions.assertEquals(expectedDealershipOutgoingDto.getId(), myDealershipOutgoingDto.getId());
        Assertions.assertEquals(expectedDealershipOutgoingDto.getStreet(), myDealershipOutgoingDto.getStreet());
        Assertions.assertEquals(expectedDealershipOutgoingDto.getCity(), myDealershipOutgoingDto.getCity());
        Assertions.assertEquals(expectedDealershipOutgoingDto.getHouseNum(), myDealershipOutgoingDto.getHouseNum());
        Assertions.assertEquals(expectedDealershipOutgoingDto.getBikes().size(), myDealershipOutgoingDto.getBikes().size());
    }

    @Test
    void deleteBikeFromDealershipNoDealership() {
        Mockito.doReturn(false).when(dealershipRepository).existsById(Mockito.any());
        NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
                () -> dealershipService.deleteBikeFromDealership(1L, 1L));
        Assertions.assertEquals("Dealership not found", ex.getMessage());
    }

    @Test
    void deleteBikeFromDealershipNoBike() {
        Mockito.doReturn(true).when(dealershipRepository).existsById(Mockito.any());
        Mockito.doReturn(false).when(bikeRepository).existsById(Mockito.any());
        NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
                () -> dealershipService.deleteBikeFromDealership(1L, 1L));
        Assertions.assertEquals("Bike not found", ex.getMessage());
    }

    @Test
    void deleteBikeFromDealershipEmptyDealership() {
        Mockito.doReturn(true).when(dealershipRepository).existsById(Mockito.any());
        Mockito.doReturn(true).when(bikeRepository).existsById(Mockito.any());
        Mockito.doReturn(Optional.empty()).when(dealershipRepository).findById(Mockito.anyLong());
        EntityNotFoundException ex = Assertions.assertThrows(EntityNotFoundException.class,
                () -> dealershipService.deleteBikeFromDealership(1L, 1L));
        Assertions.assertEquals("Dealership not found", ex.getMessage());
    }

    @Test
    void deleteBikeFromDealershipEmptyBike() {
        Mockito.doReturn(true).when(dealershipRepository).existsById(Mockito.any());
        Mockito.doReturn(true).when(bikeRepository).existsById(Mockito.any());
        Mockito.doReturn(Optional.of(new Dealership())).when(dealershipRepository).findById(Mockito.anyLong());
        Mockito.doReturn(Optional.empty()).when(bikeRepository).findById(Mockito.any());
        EntityNotFoundException ex = Assertions.assertThrows(EntityNotFoundException.class,
                () -> dealershipService.deleteBikeFromDealership(1L, 1L));
        Assertions.assertEquals("Bike not found", ex.getMessage());
    }
}
