package aston.bootcamp.dto;

import aston.bootcamp.dto.mapper.BrandDtoMapper;
import aston.bootcamp.dto.mapper.BrandDtoMapperImpl;
import aston.bootcamp.model.Brand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BrandDtoMapperTest {
    private final BrandDtoMapper mapper = new BrandDtoMapperImpl();

    @Test
    void normalToOutgoing() {
        Brand brand = new Brand(1L, "test", new ArrayList<>());
        BrandOutgoingDto expectedResult = new BrandOutgoingDto(1L, "test", new ArrayList<>());
        BrandOutgoingDto result = mapper.brandToBrandOutgoingDto(brand);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void incomingToNormal() {
        Brand expectedResult = new Brand(null, "test", new ArrayList<>());
        BrandIncomingDto brand = new BrandIncomingDto("test");
        Brand result = mapper.brandIncomingDtoToBrand(brand);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void updateToNormal() {
        Brand expectedResult = new Brand(1L, "test", new ArrayList<>());
        BrandUpdateDto brand = new BrandUpdateDto(1L, "test", new ArrayList<>());
        Brand result = mapper.brandUpdateDtoToBrand(brand);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void normalsToOutgoings() {
        List<Brand> brands = List.of(new Brand(1L, "test", new ArrayList<>()),
                new Brand(2L, "test", new ArrayList<>()));
        List<BrandOutgoingDto> expectedResult = List.of(new BrandOutgoingDto(1L, "test", new ArrayList<>()),
                new BrandOutgoingDto(2L, "test", new ArrayList<>()));
        List<BrandOutgoingDto> result = mapper.brandsToBrandOutgoingDto(brands);
        Assertions.assertEquals(expectedResult.size(), result.size());
        Assertions.assertEquals(expectedResult.get(0), result.get(0));
        Assertions.assertEquals(expectedResult.get(1), result.get(1));
    }
}
