package aston.bootcamp.dto.mapper;

import aston.bootcamp.dto.DealershipIncomingDto;
import aston.bootcamp.dto.DealershipOutgoingDto;
import aston.bootcamp.dto.DealershipUpdateDto;
import aston.bootcamp.model.Dealership;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DealershipDtoMapper {
    BikeDtoMapper bikeMapper = new BikeDtoMapperImpl();
    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "city", expression = "java(dealershipIncomingDto.getCity())")
    @Mapping(target = "street", expression = "java(dealershipIncomingDto.getStreet())")
    @Mapping(target = "houseNum", expression = "java(dealershipIncomingDto.getHouseNum())")
    @Mapping(target = "bikes", expression = "java(null)")
    Dealership dealershipIncomingDtoToDealership(DealershipIncomingDto dealershipIncomingDto);

    @Mapping(target = "id", expression = "java(dealership.getId())")
    @Mapping(target = "city", expression = "java(dealership.getCity())")
    @Mapping(target = "street", expression = "java(dealership.getStreet())")
    @Mapping(target = "houseNum", expression = "java(dealership.getHouseNum())")
    @Mapping(target = "bikes", expression = "java(bikeMapper.bikesToBikesOutgoingDto(dealership.getBikes()))")
    DealershipOutgoingDto dealershipToDealershipOutgoingDto(Dealership dealership);

    @Mapping(target = "id", expression = "java(dealershipUpdateDto.getId())")
    @Mapping(target = "city", expression = "java(dealershipUpdateDto.getCity())")
    @Mapping(target = "street", expression = "java(dealershipUpdateDto.getStreet())")
    @Mapping(target = "houseNum", expression = "java(dealershipUpdateDto.getHouseNum())")
    @Mapping(target = "bikes", expression = "java(bikeMapper.bikesUpdateDtoToBikes(dealershipUpdateDto.getBikes()))")
    Dealership dealershipUpdateDtoToDealership(DealershipUpdateDto dealershipUpdateDto);

    List<DealershipOutgoingDto> listDealershipToListDealershipOutgoingDto(List<Dealership> dealerships);

    List<Dealership> listDealershipUpdateDtoToListDealership(List<DealershipUpdateDto> dealershipUpdateDtoList);
}
