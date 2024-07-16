package aston.bootcamp.dto;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BikeUpdateDto that = (BikeUpdateDto) o;
        return Objects.equals(id, that.id) && Objects.equals(type, that.type) && Objects.equals(brand, that.brand) && Objects.equals(model, that.model) && Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, brand, model, cost);
    }
}
