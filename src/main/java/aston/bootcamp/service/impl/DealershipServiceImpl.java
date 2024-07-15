package aston.bootcamp.service.impl;

import aston.bootcamp.dto.DealershipIncomingDto;
import aston.bootcamp.dto.DealershipOutgoingDto;
import aston.bootcamp.dto.DealershipUpdateDto;
import aston.bootcamp.dto.mapper.DealershipDtoMapper;
import aston.bootcamp.dto.mapper.DealershipDtoMapperImpl;
import aston.bootcamp.exceptions.NotFoundException;
import aston.bootcamp.model.Bike;
import aston.bootcamp.model.Dealership;
import aston.bootcamp.repository.BikeRepository;
import aston.bootcamp.repository.DealershipRepository;
import aston.bootcamp.service.DealershipService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DealershipServiceImpl implements DealershipService {
    private final DealershipRepository dealershipRepository;
    private final BikeRepository bikeRepository;
    private final DealershipDtoMapper mapper = new DealershipDtoMapperImpl();

    @Autowired
    public DealershipServiceImpl(DealershipRepository dealershipRepository, BikeRepository bikeRepository) {
        this.dealershipRepository = dealershipRepository;
        this.bikeRepository = bikeRepository;
    }

    private void checkExists(Long id) throws NotFoundException {
        if (!dealershipRepository.existsById(id)) {
            throw new NotFoundException("Dealership not found");
        }
    }

    @Override
    @Transactional
    public List<DealershipOutgoingDto> getAllDealerships() {
        return mapper.listDealershipToListDealershipOutgoingDto(dealershipRepository.findAll());
    }

    @Override
    @Transactional
    public DealershipOutgoingDto getDealershipById(Long id) throws NotFoundException {
        checkExists(id);
        return mapper.dealershipToDealershipOutgoingDto(dealershipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dealership not found")));
    }

    @Override
    @Transactional
    public DealershipOutgoingDto createDealership(DealershipIncomingDto dealership) {
        Dealership dealershipForCreate = mapper.dealershipIncomingDtoToDealership(dealership);
        return mapper.dealershipToDealershipOutgoingDto(dealershipRepository.save(dealershipForCreate));
    }

    @Override
    @Transactional
    public DealershipOutgoingDto updateDealership(Long id, DealershipUpdateDto dealershipUpdateDto) throws NotFoundException {
        if (dealershipUpdateDto == null || dealershipUpdateDto.getId() == null || id == null) {
            throw new IllegalArgumentException("Incorrect dealership params");
        }
        checkExists(id);
        if (!dealershipUpdateDto.getId().equals(id)) {
            dealershipRepository.deleteById(id);
        }
        Dealership dealership = mapper.dealershipUpdateDtoToDealership(dealershipUpdateDto);
        return mapper.dealershipToDealershipOutgoingDto(dealershipRepository.save(dealership));
    }

    @Override
    @Transactional
    public void deleteDealershipById(Long id) throws NotFoundException {
        checkExists(id);
        dealershipRepository.deleteById(id);
    }

    @Override
    @Transactional
    public DealershipOutgoingDto addBikeToDealership(Long dealershipId, Long bikeId) throws NotFoundException {
        checkExists(dealershipId);
        if (!bikeRepository.existsById(bikeId)) {
            throw new NotFoundException("Bike not found");
        }
        Dealership dealership = dealershipRepository.findById(dealershipId).orElseThrow(() -> new EntityNotFoundException("Dealership not found"));
        Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new EntityNotFoundException("Bike not found"));
        List<Bike> bikes = dealership.getBikes();
        bikes.add(bike);
        dealership.setBikes(bikes);
        return mapper.dealershipToDealershipOutgoingDto(dealershipRepository.save(dealership));
    }

    @Override
    @Transactional
    public DealershipOutgoingDto deleteBikeFromDealership(Long dealershipId, Long bikeId) throws NotFoundException {
        checkExists(dealershipId);
        if (!bikeRepository.existsById(bikeId)) {
            throw new NotFoundException("Bike not found");
        }
        Dealership dealership = dealershipRepository.findById(dealershipId).orElseThrow(() -> new EntityNotFoundException("Dealership not found"));
        Bike bike = bikeRepository.findById(bikeId).orElseThrow(() -> new EntityNotFoundException("Bike not found"));
        List<Bike> bikes = dealership.getBikes();
        bikes.remove(bike);
        dealership.setBikes(bikes);
        return mapper.dealershipToDealershipOutgoingDto(dealershipRepository.save(dealership));
    }
}
