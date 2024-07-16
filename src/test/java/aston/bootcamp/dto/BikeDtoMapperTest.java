package aston.bootcamp.dto;


import aston.bootcamp.dto.mapper.BikeDtoMapper;
import aston.bootcamp.dto.mapper.BikeDtoMapperImpl;
import aston.bootcamp.model.Bike;
import aston.bootcamp.model.Brand;
import aston.bootcamp.model.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BikeDtoMapperTest {
    private final BikeDtoMapper mapper = new BikeDtoMapperImpl();

    @Test
    void normalToOutgoing() {
        Bike bike = new Bike(1L,
                new Type(1L, "test", new ArrayList<>()),
                new Brand(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>());
        BikeOutgoingDto expectedResult = new BikeOutgoingDto(1L,
                new TypeOutgoingDto(1L, "test", new ArrayList<>()),
                new BrandOutgoingDto(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>());
        BikeOutgoingDto result = mapper.bikeToBikeOutgoingDto(bike);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void incomingToNormal() {
        Bike expectedResult = new Bike(null,
                new Type(1L, "test", new ArrayList<>()),
                new Brand(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>());
        BikeIncomingDto bike = new BikeIncomingDto(
                new Type(1L, "test", new ArrayList<>()),
                new Brand(1L, "test", new ArrayList<>()),
                "test",
                100L);
        Bike result = mapper.bikeIncomingDtoToBike(bike);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void updateToNormal() {
        BikeUpdateDto bike = new BikeUpdateDto(1L,
                new TypeUpdateDto(1L, "test", new ArrayList<>()),
                new BrandUpdateDto(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>());
        Bike expectedResult = new Bike(1L,
                new Type(1L, "test", new ArrayList<>()),
                new Brand(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>());
        Bike result = mapper.bikeUpdateDtoToBike(bike);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void normalsToOutgoings() {
        List<Bike> bikes = List.of(new Bike(1L,
                new Type(1L, "test", new ArrayList<>()),
                new Brand(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>()), new Bike(2L,
                new Type(1L, "test", new ArrayList<>()),
                new Brand(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>()));
        List<BikeOutgoingDto> expectedResult = List.of(new BikeOutgoingDto(1L,
                new TypeOutgoingDto(1L, "test", new ArrayList<>()),
                new BrandOutgoingDto(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>()), new BikeOutgoingDto(2L,
                new TypeOutgoingDto(1L, "test", new ArrayList<>()),
                new BrandOutgoingDto(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>()));
        List<BikeOutgoingDto> result = mapper.bikesToBikesOutgoingDto(bikes);
        Assertions.assertEquals(expectedResult.size(), result.size());
        Assertions.assertEquals(expectedResult.get(0), result.get(0));
        Assertions.assertEquals(expectedResult.get(1), result.get(1));
    }

    @Test
    void updatesToNormals() {
        List<Bike> expectedResult = List.of(new Bike(1L,
                new Type(1L, "test", new ArrayList<>()),
                new Brand(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>()), new Bike(2L,
                new Type(1L, "test", new ArrayList<>()),
                new Brand(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>()));
        List<BikeUpdateDto> bikes = List.of(new BikeUpdateDto(1L,
                new TypeUpdateDto(1L, "test", new ArrayList<>()),
                new BrandUpdateDto(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>()), new BikeUpdateDto(2L,
                new TypeUpdateDto(1L, "test", new ArrayList<>()),
                new BrandUpdateDto(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>()));
        List<Bike> result = mapper.bikesUpdateDtoToBikes(bikes);
        Assertions.assertEquals(expectedResult.size(), result.size());
        Assertions.assertEquals(expectedResult.get(0), result.get(0));
        Assertions.assertEquals(expectedResult.get(1), result.get(1));
    }
}
