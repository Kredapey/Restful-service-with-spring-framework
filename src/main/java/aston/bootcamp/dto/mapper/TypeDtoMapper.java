package aston.bootcamp.dto.mapper;

import aston.bootcamp.dto.TypeIncomingDto;
import aston.bootcamp.dto.TypeOutgoingDto;
import aston.bootcamp.dto.TypeUpdateDto;
import aston.bootcamp.model.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface TypeDtoMapper {
    BikeDtoMapper bikeMapper = new BikeDtoMapperImpl();

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "type", expression = "java(typeIncomingDto.getType())")
    @Mapping(target = "bikes", expression = "java(null)")
    Type typeIncomingDtoToType(TypeIncomingDto typeIncomingDto);

    @Mapping(target = "id", expression = "java(type.getId())")
    @Mapping(target = "type", expression = "java(type.getType())")
    @Mapping(target = "bikes", expression = "java(bikeMapper.bikesToBikesOutgoingDto(type.getBikes()))")
    TypeOutgoingDto typeToTypeOutgoingDto(Type type);

    @Mapping(target = "id", expression = "java(typeUpdateDto.getId())")
    @Mapping(target = "type", expression = "java(typeUpdateDto.getType())")
    @Mapping(target = "bikes", expression = "java(bikeMapper.bikesUpdateDtoToBikes(typeUpdateDto.getBikes()))")
    Type typeUpdateDtoToType(TypeUpdateDto typeUpdateDto);

    List<TypeOutgoingDto> typesToTypesOutgoingDto(List<Type> types);
}
