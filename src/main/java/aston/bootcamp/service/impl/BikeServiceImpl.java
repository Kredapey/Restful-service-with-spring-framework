package aston.bootcamp.service.impl;

import aston.bootcamp.dto.*;
import aston.bootcamp.dto.mapper.BikeDtoMapper;
import aston.bootcamp.dto.mapper.BikeDtoMapperImpl;
import aston.bootcamp.exceptions.NotFoundException;
import aston.bootcamp.model.Bike;
import aston.bootcamp.repository.BikeRepository;
import aston.bootcamp.service.BikeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BikeServiceImpl implements BikeService {

    private final BikeRepository bikeRepository;
    private final BikeDtoMapper mapper = new BikeDtoMapperImpl();

    @Autowired
    public BikeServiceImpl(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    private void checkExists(Long id) throws NotFoundException {
        if (!bikeRepository.existsById(id)) {
            throw new NotFoundException("Bike not found");
        }
    }

    @Override
    @Transactional
    public List<BikeOutgoingDto> getAllBikes() {
        return mapper.bikesToBikesOutgoingDto(bikeRepository.findAll());
    }

    @Override
    @Transactional
    public BikeOutgoingDto getBikeById(Long id) throws NotFoundException {
        checkExists(id);
        return mapper.bikeToBikeOutgoingDto(bikeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bike not found")));
    }

    @Override
    @Transactional
    public BikeOutgoingDto createBike(BikeIncomingDto bike) {
        Bike bikeForCreate = mapper.bikeIncomingDtoToBike(bike);
        return mapper.bikeToBikeOutgoingDto(bikeRepository.save(bikeForCreate));
    }

    @Override
    @Transactional
    public BikeOutgoingDto updateBike(Long id, BikeUpdateDto bikeUpdateDto) throws NotFoundException {
        if (bikeUpdateDto == null || bikeUpdateDto.getId() == null || id == null) {
            throw new IllegalArgumentException("Incorrect bike params");
        }
        checkExists(id);
        if (!bikeUpdateDto.getId().equals(id)) {
            bikeRepository.deleteById(id);
        }
        Bike bike = mapper.bikeUpdateDtoToBike(bikeUpdateDto);
        return mapper.bikeToBikeOutgoingDto(bikeRepository.save(bike));
    }

    @Override
    @Transactional
    public void deleteByBikeId(Long id) throws NotFoundException {
        checkExists(id);
        bikeRepository.deleteById(id);
    }
}
