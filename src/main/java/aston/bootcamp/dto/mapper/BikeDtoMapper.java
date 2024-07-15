package aston.bootcamp.dto.mapper;

import aston.bootcamp.dto.BikeIncomingDto;
import aston.bootcamp.dto.BikeOutgoingDto;
import aston.bootcamp.dto.BikeUpdateDto;
import aston.bootcamp.model.Bike;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface BikeDtoMapper {
    DealershipDtoMapper dealershipMapper = new DealershipDtoMapperImpl();
    BrandDtoMapper brandMapper = new BrandDtoMapperImpl();
    TypeDtoMapper typeMapper = new TypeDtoMapperImpl();
    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "type", expression = "java(bikeIncomingDto.getType())")
    @Mapping(target = "brand", expression = "java(bikeIncomingDto.getBrand())")
    @Mapping(target = "model", expression = "java(bikeIncomingDto.getModel())")
    @Mapping(target = "cost", expression = "java(bikeIncomingDto.getCost())")
    @Mapping(target = "dealerships", expression = "java(null)")
    Bike bikeIncomingDtoToBike(BikeIncomingDto bikeIncomingDto);

    @Mapping(target = "id", expression = "java(bike.getId())")
    @Mapping(target = "type", expression = "java(typeMapper.typeToTypeOutgoingDto(bike.getType()))")
    @Mapping(target = "brand", expression = "java(brandMapper.brandToBrandOutgoingDto(bike.getBrand()))")
    @Mapping(target = "model", expression = "java(bike.getModel())")
    @Mapping(target = "cost", expression = "java(bike.getCost())")
    @Mapping(target = "dealerships", expression = "java(dealershipMapper.listDealershipToListDealershipOutgoingDto(bike.getDealerships()))")
    BikeOutgoingDto bikeToBikeOutgoingDto(Bike bike);

    @Mapping(target = "id", expression = "java(bikeUpdateDto.getId())")
    @Mapping(target = "type", expression = "java(typeMapper.typeUpdateDtoToType(bikeUpdateDto.getType()))")
    @Mapping(target = "brand", expression = "java(brandMapper.brandUpdateDtoToBrand(bikeUpdateDto.getBrand()))")
    @Mapping(target = "model", expression = "java(bikeUpdateDto.getModel())")
    @Mapping(target = "cost", expression = "java(bikeUpdateDto.getCost())")
    @Mapping(target = "dealerships", expression = "java(dealershipMapper.listDealershipUpdateDtoToListDealership(bikeUpdateDto.getDealerships()))")
    Bike bikeUpdateDtoToBike(BikeUpdateDto bikeUpdateDto);

    List<BikeOutgoingDto> bikesToBikesOutgoingDto(List<Bike> bikes);

    List<Bike> bikesUpdateDtoToBikes(List<BikeUpdateDto> bikeUpdateDtoList);
}
