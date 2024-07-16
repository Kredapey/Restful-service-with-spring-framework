package aston.bootcamp.dto;

import java.util.List;
import java.util.Objects;

public class BikeOutgoingDto {
    private Long id;
    private TypeOutgoingDto type;
    private BrandOutgoingDto brand;
    private String model;
    private Long cost;
    private List<DealershipOutgoingDto> dealerships;

    public BikeOutgoingDto() {
    }

    public BikeOutgoingDto(Long id, TypeOutgoingDto type, BrandOutgoingDto brand, String model, Long cost, List<DealershipOutgoingDto> dealerships) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.cost = cost;
        this.dealerships = dealerships;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(TypeOutgoingDto type) {
        this.type = type;
    }

    public void setBrand(BrandOutgoingDto brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public void setDealerships(List<DealershipOutgoingDto> dealerships) {
        this.dealerships = dealerships;
    }

    public Long getId() {
        return id;
    }

    public TypeOutgoingDto getType() {
        return type;
    }

    public BrandOutgoingDto getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Long getCost() {
        return cost;
    }

    public List<DealershipOutgoingDto> getDealerships() {
        return dealerships;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BikeOutgoingDto that = (BikeOutgoingDto) o;
        return Objects.equals(id, that.id) && Objects.equals(type, that.type) && Objects.equals(brand, that.brand) && Objects.equals(model, that.model) && Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, brand, model, cost);
    }

}
