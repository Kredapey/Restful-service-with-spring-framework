package aston.bootcamp.dto;

import aston.bootcamp.model.Brand;
import aston.bootcamp.model.Type;

import java.util.Objects;

public class BikeIncomingDto {
    private Type type;
    private Brand brand;
    private String model;
    private Long cost;

    public BikeIncomingDto() {
    }

    public BikeIncomingDto(Type type, Brand brand, String model, Long cost) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.cost = cost;
    }

    public Type getType() {
        return type;
    }

    public Brand getBrand() {
        return brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BikeIncomingDto that = (BikeIncomingDto) o;
        return Objects.equals(type, that.type) && Objects.equals(brand, that.brand) && Objects.equals(model, that.model) && Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, brand, model, cost);
    }

    public String getModel() {
        return model;
    }

    public Long getCost() {
        return cost;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }
}
