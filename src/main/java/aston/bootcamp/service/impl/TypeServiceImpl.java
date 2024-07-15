package aston.bootcamp.service.impl;

import aston.bootcamp.dto.TypeIncomingDto;
import aston.bootcamp.dto.TypeOutgoingDto;
import aston.bootcamp.dto.TypeUpdateDto;
import aston.bootcamp.dto.mapper.TypeDtoMapper;
import aston.bootcamp.dto.mapper.TypeDtoMapperImpl;
import aston.bootcamp.exceptions.NotFoundException;
import aston.bootcamp.model.Type;
import aston.bootcamp.repository.TypeRepository;
import aston.bootcamp.service.TypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;
    private final TypeDtoMapper mapper = new TypeDtoMapperImpl();

    @Autowired
    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    private void checkExists(Long id) throws NotFoundException {
        if (!typeRepository.existsById(id)) {
            throw new NotFoundException("Type not found");
        }
    }

    @Override
    @Transactional
    public List<TypeOutgoingDto> getAllTypes() {
        return mapper.typesToTypesOutgoingDto(typeRepository.findAll());
    }

    @Override
    @Transactional
    public TypeOutgoingDto getByTypeId(Long id) throws NotFoundException {
        checkExists(id);
        return mapper.typeToTypeOutgoingDto(typeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Type not found")));
    }

    @Override
    @Transactional
    public TypeOutgoingDto createType(TypeIncomingDto type) {
        Type typeForCreate = mapper.typeIncomingDtoToType(type);
        return mapper.typeToTypeOutgoingDto(typeRepository.save(typeForCreate));
    }

    @Override
    @Transactional
    public TypeOutgoingDto updateType(Long id, TypeUpdateDto typeUpdateDto) throws NotFoundException {
        if (typeUpdateDto == null || typeUpdateDto.getId() == null || id == null) {
            throw new IllegalArgumentException("Incorrect type params");
        }
        checkExists(id);
        if (!typeUpdateDto.getId().equals(id)) {
            typeRepository.deleteById(id);
        }
        Type brand = mapper.typeUpdateDtoToType(typeUpdateDto);
        return mapper.typeToTypeOutgoingDto(typeRepository.save(brand));
    }

    @Override
    @Transactional
    public void deleteByTypeId(Long id) throws NotFoundException {
        checkExists(id);
        typeRepository.deleteById(id);
    }
}
