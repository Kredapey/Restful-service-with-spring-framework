package aston.bootcamp.service;

import aston.bootcamp.dto.TypeIncomingDto;
import aston.bootcamp.dto.TypeOutgoingDto;
import aston.bootcamp.dto.TypeUpdateDto;
import aston.bootcamp.exceptions.NotFoundException;

import java.util.List;

public interface TypeService {
    List<TypeOutgoingDto> getAllTypes();
    TypeOutgoingDto getByTypeId(Long id) throws NotFoundException;
    TypeOutgoingDto createType(TypeIncomingDto type);
    TypeOutgoingDto updateType(Long id, TypeUpdateDto typeUpdateDto) throws NotFoundException;
    void deleteByTypeId(Long id) throws NotFoundException;
}
