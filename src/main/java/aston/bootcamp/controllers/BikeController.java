package aston.bootcamp.controllers;

import aston.bootcamp.dto.BikeIncomingDto;
import aston.bootcamp.dto.BikeOutgoingDto;
import aston.bootcamp.dto.BikeUpdateDto;
import aston.bootcamp.exceptions.NotFoundException;
import aston.bootcamp.service.BikeService;
import aston.bootcamp.service.impl.BikeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/bike")
public class BikeController {
    private final BikeService bikeService;
    @Autowired
    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping("/all")
    public List<BikeOutgoingDto> findAllBikes() {
        return bikeService.getAllBikes();
    }

    @GetMapping("/{id}")
    public BikeOutgoingDto findBikeById(@PathVariable(name = "id") Long id) throws NotFoundException {
        return bikeService.getBikeById(id);
    }

    @PostMapping
    public ResponseEntity<BikeOutgoingDto> createBike(@RequestBody BikeIncomingDto bike) {
        BikeOutgoingDto created = bikeService.createBike(bike);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BikeOutgoingDto> updateBike(@PathVariable(name = "id") Long id,
                                                            @RequestBody BikeUpdateDto updatedBike)
            throws NotFoundException {
        BikeOutgoingDto updated = bikeService.updateBike(id, updatedBike);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBike(@PathVariable(name = "id") Long id) throws NotFoundException {
        bikeService.deleteByBikeId(id);
        return ResponseEntity.noContent().build();
    }
}
