package aston.bootcamp.service;

import aston.bootcamp.dto.BrandIncomingDto;
import aston.bootcamp.dto.BrandOutgoingDto;
import aston.bootcamp.dto.BrandUpdateDto;
import aston.bootcamp.exceptions.NotFoundException;

import java.util.List;

public interface BrandService {
    List<BrandOutgoingDto> getAllBrands();

    BrandOutgoingDto getBrandById(Long id) throws NotFoundException;

    BrandOutgoingDto createBrand(BrandIncomingDto brand);

    BrandOutgoingDto updateBrand(Long id, BrandUpdateDto brandUpdateDto) throws NotFoundException;

    void deleteByBrandId(Long id) throws NotFoundException;
}
