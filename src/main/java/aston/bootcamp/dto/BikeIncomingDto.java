package aston.bootcamp.dto;

import aston.bootcamp.model.Brand;
import aston.bootcamp.model.Type;

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
