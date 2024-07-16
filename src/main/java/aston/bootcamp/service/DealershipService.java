package aston.bootcamp.service;

import aston.bootcamp.dto.DealershipIncomingDto;
import aston.bootcamp.dto.DealershipOutgoingDto;
import aston.bootcamp.dto.DealershipUpdateDto;
import aston.bootcamp.exceptions.NotFoundException;

import java.util.List;

public interface DealershipService {
    List<DealershipOutgoingDto> getAllDealerships();

    DealershipOutgoingDto getDealershipById(Long id) throws NotFoundException;

    DealershipOutgoingDto createDealership(DealershipIncomingDto dealership);

    DealershipOutgoingDto updateDealership(Long id, DealershipUpdateDto dealershipUpdateDto) throws NotFoundException;

    void deleteDealershipById(Long id) throws NotFoundException;

    DealershipOutgoingDto addBikeToDealership(Long dealershipId, Long bikeId) throws NotFoundException;

    DealershipOutgoingDto deleteBikeFromDealership(Long dealershipId, Long bikeId) throws NotFoundException;
}
