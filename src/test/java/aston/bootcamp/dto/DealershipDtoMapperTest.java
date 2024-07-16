package aston.bootcamp.dto;

import aston.bootcamp.dto.mapper.DealershipDtoMapper;
import aston.bootcamp.dto.mapper.DealershipDtoMapperImpl;
import aston.bootcamp.model.Dealership;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DealershipDtoMapperTest {
    private final DealershipDtoMapper mapper = new DealershipDtoMapperImpl();

    @Test
    void normalToOutgoing() {
        Dealership dealership = new Dealership(1L, "test", "test", 1L, new ArrayList<>());
        DealershipOutgoingDto expectedResult = new DealershipOutgoingDto(1L,
                "test",
                "test",
                1L,
                new ArrayList<>());
        DealershipOutgoingDto result = mapper.dealershipToDealershipOutgoingDto(dealership);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void incomingToNormal() {
        Dealership expectedResult = new Dealership(null, "test", "test", 1L, new ArrayList<>());
        DealershipIncomingDto dealership = new DealershipIncomingDto(
                "test",
                "test",
                1L);
        Dealership result = mapper.dealershipIncomingDtoToDealership(dealership);
        Assertions.assertEquals(dealership, new DealershipIncomingDto("test", "test", 1L));
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void updateToNormal() {
        Dealership expectedResult = new Dealership(1L, "test", "test", 1L, new ArrayList<>());
        DealershipUpdateDto dealership = new DealershipUpdateDto(1L,
                "test",
                "test",
                1L,
                new ArrayList<>());
        Dealership result = mapper.dealershipUpdateDtoToDealership(dealership);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void normalsToOutgoings() {
        List<Dealership> dealerships = List.of(new Dealership(1L,
                        "test",
                        "test",
                        1L,
                        new ArrayList<>()),
                new Dealership(2L,
                        "test",
                        "test",
                        1L,
                        new ArrayList<>()));
        List<DealershipOutgoingDto> expectedResult = List.of(new DealershipOutgoingDto(1L,
                        "test",
                        "test",
                        1L,
                        new ArrayList<>()),
                new DealershipOutgoingDto(2L,
                        "test",
                        "test",
                        1L,
                        new ArrayList<>()));
        List<DealershipOutgoingDto> result = mapper.listDealershipToListDealershipOutgoingDto(dealerships);
        Assertions.assertEquals(expectedResult.size(), result.size());
        Assertions.assertEquals(expectedResult.get(0), result.get(0));
        Assertions.assertEquals(expectedResult.get(1), result.get(1));
    }

    @Test
    void updatesToNormals() {
        List<Dealership> expectedResult = List.of(new Dealership(1L,
                        "test",
                        "test",
                        1L,
                        new ArrayList<>()),
                new Dealership(2L,
                        "test",
                        "test",
                        1L,
                        new ArrayList<>()));
        List<DealershipUpdateDto> dealerships = List.of(new DealershipUpdateDto(1L,
                        "test",
                        "test",
                        1L,
                        new ArrayList<>()),
                new DealershipUpdateDto(2L,
                        "test",
                        "test",
                        1L,
                        new ArrayList<>()));
        List<Dealership> result = mapper.listDealershipUpdateDtoToListDealership(dealerships);
        Assertions.assertEquals(expectedResult.size(), result.size());
        Assertions.assertEquals(expectedResult.get(0), result.get(0));
        Assertions.assertEquals(expectedResult.get(1), result.get(1));
    }
}
