package aston.bootcamp.service.impl;

import aston.bootcamp.dto.BrandIncomingDto;
import aston.bootcamp.dto.BrandOutgoingDto;
import aston.bootcamp.dto.BrandUpdateDto;
import aston.bootcamp.dto.mapper.BrandDtoMapper;
import aston.bootcamp.dto.mapper.BrandDtoMapperImpl;
import aston.bootcamp.exceptions.NotFoundException;
import aston.bootcamp.model.Brand;
import aston.bootcamp.repository.BrandRepository;
import aston.bootcamp.service.BrandService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandDtoMapper mapper = new BrandDtoMapperImpl();

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    private void checkExists(Long id) throws NotFoundException {
        if (!brandRepository.existsById(id)) {
            throw new NotFoundException("Brand not found");
        }
    }

    @Override
    @Transactional
    public List<BrandOutgoingDto> getAllBrands() {
        return mapper.brandsToBrandOutgoingDto(brandRepository.findAll());
    }

    @Override
    @Transactional
    public BrandOutgoingDto getBrandById(Long id) throws NotFoundException {
        checkExists(id);
        return mapper.brandToBrandOutgoingDto(brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found")));
    }

    @Override
    @Transactional
    public BrandOutgoingDto createBrand(BrandIncomingDto brand) {
        Brand brandForCreate = mapper.brandIncomingDtoToBrand(brand);
        return mapper.brandToBrandOutgoingDto(brandRepository.save(brandForCreate));
    }

    @Override
    @Transactional
    public BrandOutgoingDto updateBrand(Long id, BrandUpdateDto brandUpdateDto) throws NotFoundException {
        if (brandUpdateDto == null || brandUpdateDto.getId() == null || id == null) {
            throw new IllegalArgumentException("Incorrect brand params");
        }
        checkExists(id);
        if (!brandUpdateDto.getId().equals(id)) {
            brandRepository.deleteById(id);
        }
        Brand brand = mapper.brandUpdateDtoToBrand(brandUpdateDto);
        return mapper.brandToBrandOutgoingDto(brandRepository.save(brand));
    }

    @Override
    @Transactional
    public void deleteByBrandId(Long id) throws NotFoundException {
        checkExists(id);
        brandRepository.deleteById(id);
    }
}
