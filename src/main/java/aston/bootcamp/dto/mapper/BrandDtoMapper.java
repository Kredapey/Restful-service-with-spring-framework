package aston.bootcamp.dto.mapper;

import aston.bootcamp.dto.BrandIncomingDto;
import aston.bootcamp.dto.BrandOutgoingDto;
import aston.bootcamp.dto.BrandUpdateDto;
import aston.bootcamp.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface BrandDtoMapper {
    BikeDtoMapper bikeMapper = new BikeDtoMapperImpl();
    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "brand", expression = "java(brandIncomingDto.getBrand())")
    @Mapping(target = "bikes", expression = "java(null)")
    Brand brandIncomingDtoToBrand(BrandIncomingDto brandIncomingDto);

    @Mapping(target = "id", expression = "java(brand.getId())")
    @Mapping(target = "brand", expression = "java(brand.getBrand())")
    @Mapping(target = "bikes", expression = "java(bikeMapper.bikesToBikesOutgoingDto(brand.getBikes()))")
    BrandOutgoingDto brandToBrandOutgoingDto(Brand brand);

    @Mapping(target = "id", expression = "java(brandUpdateDto.getId())")
    @Mapping(target = "brand", expression = "java(brandUpdateDto.getBrand())")
    @Mapping(target = "bikes", expression = "java(bikeMapper.bikesUpdateDtoToBikes(brandUpdateDto.getBikes()))")
    Brand brandUpdateDtoToBrand(BrandUpdateDto brandUpdateDto);

    List<BrandOutgoingDto> brandsToBrandOutgoingDto(List<Brand> brands);
}
