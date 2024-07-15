package aston.bootcamp.service;

import aston.bootcamp.config.service.BrandServiceTestConfig;
import aston.bootcamp.dto.BrandIncomingDto;
import aston.bootcamp.dto.BrandOutgoingDto;
import aston.bootcamp.dto.BrandUpdateDto;
import aston.bootcamp.exceptions.NotFoundException;
import aston.bootcamp.model.Brand;
import aston.bootcamp.repository.BrandRepository;
import aston.bootcamp.service.impl.BrandServiceImpl;
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
@ContextConfiguration(classes = {BrandServiceTestConfig.class})
public class BrandServiceTest {
    @Autowired
    private BrandRepository brandRepository;
    @InjectMocks
    private BrandServiceImpl brandService;

    @Test
    void save() {
        Long expectedId = 1L;
        BrandIncomingDto brandIncomingDto = new BrandIncomingDto("test_brand");
        Brand brand = new Brand(expectedId, "test_brand", new ArrayList<>());
        Mockito.doReturn(brand).when(brandRepository).save(Mockito.any(Brand.class));
        BrandOutgoingDto result = brandService.createBrand(brandIncomingDto);
        Assertions.assertEquals(expectedId, result.getId());
    }

    @Test
    void update() throws NotFoundException {
        Long expectedId = 1L;
        BrandUpdateDto brandUpdateDto = new BrandUpdateDto(expectedId, "test", new ArrayList<>());
        Mockito.doReturn(true).when(brandRepository).existsById(Mockito.any());
        brandService.updateBrand(expectedId, brandUpdateDto);
        ArgumentCaptor<Brand> argumentCaptor = ArgumentCaptor.forClass(Brand.class);
        Mockito.verify(brandRepository).save(argumentCaptor.capture());
        Assertions.assertEquals(expectedId, argumentCaptor.getValue().getId());
    }

    @Test
    void updateNotFoundException() {
        BrandUpdateDto brandUpdateDto = new BrandUpdateDto(1L, "test", new ArrayList<>());
        Mockito.doReturn(false).when(brandRepository).existsById(Mockito.any());
        NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
                () -> brandService.updateBrand(1L, brandUpdateDto));
        Assertions.assertEquals("Brand not found", ex.getMessage());
    }

    @Test
    void updateIllegalArgumentException() {
        BrandUpdateDto brandUpdateDto = new BrandUpdateDto(null, "test", new ArrayList<>());
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> brandService.updateBrand(null, brandUpdateDto));
        Assertions.assertEquals("Incorrect brand params", ex.getMessage());
    }

    @Test
    void delete() throws NotFoundException {
        Long expectedId = 1L;
        Mockito.doReturn(true).when(brandRepository).existsById(Mockito.any());
        brandService.deleteByBrandId(expectedId);
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(brandRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(expectedId, argumentCaptor.getValue());
    }

    @Test
    void findById() throws NotFoundException {
        Long expectedId = 1L;
        Optional<Brand> expectedResult = Optional.of(
                new Brand(expectedId, "test", new ArrayList<>())
        );
        Mockito.doReturn(true).when(brandRepository).existsById(Mockito.any());
        Mockito.doReturn(expectedResult).when(brandRepository).findById(Mockito.any(Long.class));
        BrandOutgoingDto brandOutgoingDto = brandService.getBrandById(expectedId);
        Assertions.assertEquals(expectedId, brandOutgoingDto.getId());
    }

    @Test
    void findByIdNotFoundException() {
        Long id = 100L;
        Optional<Brand> nullOptional = Optional.empty();
        Mockito.doReturn(true).when(brandRepository).existsById(Mockito.any());
        Mockito.doReturn(nullOptional).when(brandRepository).findById(Mockito.any(Long.class));
        EntityNotFoundException ex = Assertions.assertThrows(EntityNotFoundException.class,
                () -> brandService.getBrandById(id));
        Assertions.assertEquals("Brand not found", ex.getMessage());
    }

    @Test
    void findAll() {
        List<Brand> expectedResult = List.of(
                new Brand(1L, "test", new ArrayList<>()),
                new Brand(2L, "test", new ArrayList<>())
        );
        Mockito.doReturn(expectedResult).when(brandRepository).findAll();
        List<BrandOutgoingDto> result = brandService.getAllBrands();
        Assertions.assertEquals(expectedResult.size(), result.size());
    }

}
