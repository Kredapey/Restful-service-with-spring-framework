package aston.bootcamp.dto;

import aston.bootcamp.model.Brand;
import aston.bootcamp.model.Dealership;
import aston.bootcamp.model.Type;

import java.util.List;

public class BikeUpdateDto {
    private Long id;
    private TypeUpdateDto type;
    private BrandUpdateDto brand;
    private String model;
    private Long cost;
    private List<DealershipUpdateDto> dealerships;

    public BikeUpdateDto() {
    }

    public BikeUpdateDto(Long id, TypeUpdateDto type, BrandUpdateDto brand, String model, Long cost, List<DealershipUpdateDto> dealerships) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.cost = cost;
        this.dealerships = dealerships;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeUpdateDto getType() {
        return type;
    }

    public void setType(TypeUpdateDto type) {
        this.type = type;
    }

    public BrandUpdateDto getBrand() {
        return brand;
    }

    public void setBrand(BrandUpdateDto brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public List<DealershipUpdateDto> getDealerships() {
        return dealerships;
    }

    public void setDealerships(List<DealershipUpdateDto> dealerships) {
        this.dealerships = dealerships;
    }
}
