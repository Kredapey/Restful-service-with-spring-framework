package aston.bootcamp.service;

import aston.bootcamp.dto.BikeIncomingDto;
import aston.bootcamp.dto.BikeOutgoingDto;
import aston.bootcamp.dto.BikeUpdateDto;
import aston.bootcamp.exceptions.NotFoundException;

import java.util.List;

public interface BikeService {
    List<BikeOutgoingDto> getAllBikes();
    BikeOutgoingDto getBikeById(Long id) throws NotFoundException;
    BikeOutgoingDto createBike(BikeIncomingDto bike);
    BikeOutgoingDto updateBike(Long id, BikeUpdateDto bikeUpdateDto) throws NotFoundException;
    void deleteByBikeId(Long id) throws NotFoundException;
}
