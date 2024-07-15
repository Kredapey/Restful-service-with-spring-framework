package aston.bootcamp.controllers;

import aston.bootcamp.dto.BrandIncomingDto;
import aston.bootcamp.dto.BrandOutgoingDto;
import aston.bootcamp.dto.BrandUpdateDto;
import aston.bootcamp.exceptions.NotFoundException;
import aston.bootcamp.service.BrandService;
import aston.bootcamp.service.impl.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {
    private final BrandService brandService;
    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/all")
    public List<BrandOutgoingDto> findAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public BrandOutgoingDto findBrandById(@PathVariable(name = "id") Long id) throws NotFoundException {
        return brandService.getBrandById(id);
    }

    @PostMapping
    public ResponseEntity<BrandOutgoingDto> createBrand(@RequestBody BrandIncomingDto brand) {
        BrandOutgoingDto created = brandService.createBrand(brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandOutgoingDto> updateBrand(@PathVariable(name = "id") Long id,
                                             @RequestBody BrandUpdateDto updatedBrand)
            throws NotFoundException {
        BrandOutgoingDto updated = brandService.updateBrand(id, updatedBrand);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrandById(@PathVariable(name = "id") Long id) throws NotFoundException {
        brandService.deleteByBrandId(id);
        return ResponseEntity.noContent().build();
    }
}
