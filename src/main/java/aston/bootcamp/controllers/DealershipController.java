package aston.bootcamp.controllers;

import aston.bootcamp.dto.DealershipIncomingDto;
import aston.bootcamp.dto.DealershipOutgoingDto;
import aston.bootcamp.dto.DealershipUpdateDto;
import aston.bootcamp.exceptions.NotFoundException;
import aston.bootcamp.service.DealershipService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dealership")
@Transactional
public class DealershipController {
    private final DealershipService dealershipService;

    @Autowired
    public DealershipController(DealershipService dealershipService) {
        this.dealershipService = dealershipService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DealershipOutgoingDto>> findAllDealerships() {
        return ResponseEntity.status(HttpStatus.OK).body(dealershipService.getAllDealerships());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealershipOutgoingDto> findDealershipById(@PathVariable(name = "id") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(dealershipService.getDealershipById(id));
    }

    @PostMapping
    public ResponseEntity<DealershipOutgoingDto> createDealership(@RequestBody DealershipIncomingDto dealership) {
        DealershipOutgoingDto created = dealershipService.createDealership(dealership);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DealershipOutgoingDto> updateDealership(@PathVariable(name = "id") Long id,
                                                                  @RequestBody DealershipUpdateDto updatedDealership)
            throws NotFoundException {
        DealershipOutgoingDto updated = dealershipService.updateDealership(id, updatedDealership);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{dealership_id}/add_bike/{bike_id}")
    public ResponseEntity<DealershipOutgoingDto> addBikeToDealership(
            @PathVariable(name = "dealership_id") Long dealershipId,
            @PathVariable(name = "bike_id") Long bikeId) throws NotFoundException {
        DealershipOutgoingDto result = dealershipService.addBikeToDealership(dealershipId, bikeId);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDealership(@PathVariable(name = "id") Long id) throws NotFoundException {
        dealershipService.deleteDealershipById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{dealership_id}/delete_bike/{bike_id}")
    public ResponseEntity<DealershipOutgoingDto> deleteBikeFromDealership(
            @PathVariable(name = "dealership_id") Long dealershipId,
            @PathVariable(name = "bike_id") Long bikeId) throws NotFoundException {
        DealershipOutgoingDto result = dealershipService.deleteBikeFromDealership(dealershipId, bikeId);
        return ResponseEntity.ok(result);
    }
}
