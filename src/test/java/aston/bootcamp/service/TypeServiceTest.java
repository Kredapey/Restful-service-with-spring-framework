package aston.bootcamp.service;

import aston.bootcamp.config.service.TypeServiceTestConfig;
import aston.bootcamp.dto.TypeIncomingDto;
import aston.bootcamp.dto.TypeOutgoingDto;
import aston.bootcamp.dto.TypeUpdateDto;
import aston.bootcamp.exceptions.NotFoundException;
import aston.bootcamp.model.Type;
import aston.bootcamp.repository.TypeRepository;
import aston.bootcamp.service.impl.TypeServiceImpl;
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
@ContextConfiguration(classes = {TypeServiceTestConfig.class})
public class TypeServiceTest {
    @Autowired
    TypeRepository typeRepository;
    @InjectMocks
    TypeServiceImpl typeService;

    @Test
    void save() {
        Long expectedId = 1L;
        TypeIncomingDto typeIncomingDto = new TypeIncomingDto("test_brand");
        Type type = new Type(expectedId, "test_brand", new ArrayList<>());
        Mockito.doReturn(type).when(typeRepository).save(Mockito.any(Type.class));
        TypeOutgoingDto result = typeService.createType(typeIncomingDto);
        Assertions.assertEquals(expectedId, result.getId());
    }

    @Test
    void update() throws NotFoundException {
        Long expectedId = 1L;
        TypeUpdateDto typeUpdateDto = new TypeUpdateDto(expectedId, "test", new ArrayList<>());
        Mockito.doReturn(true).when(typeRepository).existsById(Mockito.any());
        typeService.updateType(expectedId, typeUpdateDto);
        ArgumentCaptor<Type> argumentCaptor = ArgumentCaptor.forClass(Type.class);
        Mockito.verify(typeRepository).save(argumentCaptor.capture());
        Assertions.assertEquals(expectedId, argumentCaptor.getValue().getId());
    }

    @Test
    void updateNotFoundException() {
        TypeUpdateDto typeUpdateDto = new TypeUpdateDto(1L, "test", new ArrayList<>());
        Mockito.doReturn(false).when(typeRepository).existsById(Mockito.any());
        NotFoundException ex = Assertions.assertThrows(NotFoundException.class,
                () -> typeService.updateType(1L, typeUpdateDto));
        Assertions.assertEquals("Type not found", ex.getMessage());
    }

    @Test
    void updateIllegalArgumentException() {
        TypeUpdateDto typeUpdateDto = new TypeUpdateDto(null, "test", new ArrayList<>());
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> typeService.updateType(null, typeUpdateDto));
        Assertions.assertEquals("Incorrect type params", ex.getMessage());
    }

    @Test
    void delete() throws NotFoundException {
        Long expectedId = 1L;
        Mockito.doReturn(true).when(typeRepository).existsById(Mockito.any());
        typeService.deleteByTypeId(expectedId);
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(typeRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(expectedId, argumentCaptor.getValue());
    }

    @Test
    void findById() throws NotFoundException {
        Long expectedId = 1L;
        Optional<Type> expectedResult = Optional.of(
                new Type(expectedId, "test", new ArrayList<>())
        );
        Mockito.doReturn(true).when(typeRepository).existsById(Mockito.any());
        Mockito.doReturn(expectedResult).when(typeRepository).findById(Mockito.any(Long.class));
        TypeOutgoingDto typeOutgoingDto = typeService.getByTypeId(expectedId);
        Assertions.assertEquals(expectedId, typeOutgoingDto.getId());
    }

    @Test
    void findByIdNotFoundException() {
        Long id = 100L;
        Optional<Type> nullOptional = Optional.empty();
        Mockito.doReturn(true).when(typeRepository).existsById(Mockito.any());
        Mockito.doReturn(nullOptional).when(typeRepository).findById(Mockito.any(Long.class));
        EntityNotFoundException ex = Assertions.assertThrows(EntityNotFoundException.class,
                () -> typeService.getByTypeId(id));
        Assertions.assertEquals("Type not found", ex.getMessage());
    }

    @Test
    void findAll() {
        List<Type> expectedResult = List.of(
                new Type(1L, "test", new ArrayList<>()),
                new Type(2L, "test", new ArrayList<>())
        );
        Mockito.doReturn(expectedResult).when(typeRepository).findAll();
        List<TypeOutgoingDto> result = typeService.getAllTypes();
        Assertions.assertEquals(expectedResult.size(), result.size());
    }
}
