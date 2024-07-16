package aston.bootcamp.dto;

import aston.bootcamp.dto.mapper.TypeDtoMapper;
import aston.bootcamp.dto.mapper.TypeDtoMapperImpl;
import aston.bootcamp.model.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TypeDtoMapperTest {
    private final TypeDtoMapper mapper = new TypeDtoMapperImpl();

    @Test
    void normalToOutgoing() {
        Type type = new Type(1L, "test", new ArrayList<>());
        TypeOutgoingDto expectedResult = new TypeOutgoingDto(1L, "test", new ArrayList<>());
        TypeOutgoingDto result = mapper.typeToTypeOutgoingDto(type);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void incomingToNormal() {
        Type expectedResult = new Type(null, "test", new ArrayList<>());
        TypeIncomingDto brand = new TypeIncomingDto("test");
        Type result = mapper.typeIncomingDtoToType(brand);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void updateToNormal() {
        Type expectedResult = new Type(1L, "test", new ArrayList<>());
        TypeUpdateDto brand = new TypeUpdateDto(1L, "test", new ArrayList<>());
        Type result = mapper.typeUpdateDtoToType(brand);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void normalsToOutgoings() {
        List<Type> brands = List.of(new Type(1L, "test", new ArrayList<>()),
                new Type(2L, "test", new ArrayList<>()));
        List<TypeOutgoingDto> expectedResult = List.of(new TypeOutgoingDto(1L, "test", new ArrayList<>()),
                new TypeOutgoingDto(2L, "test", new ArrayList<>()));
        List<TypeOutgoingDto> result = mapper.typesToTypesOutgoingDto(brands);
        Assertions.assertEquals(expectedResult.size(), result.size());
        Assertions.assertEquals(expectedResult.get(0), result.get(0));
        Assertions.assertEquals(expectedResult.get(1), result.get(1));
    }
}
