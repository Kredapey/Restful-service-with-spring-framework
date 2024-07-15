package aston.bootcamp.dto;

import aston.bootcamp.model.Bike;

import java.util.List;

public class BrandUpdateDto {
    private Long id;
    private String brand;
    private List<BikeUpdateDto> bikes;

    public BrandUpdateDto() {
    }

    public BrandUpdateDto(Long id, String brand, List<BikeUpdateDto> bikes) {
        this.id = id;
        this.brand = brand;
        this.bikes = bikes;
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<BikeUpdateDto> getBikes() {
        return bikes;
    }

    public void setBikes(List<BikeUpdateDto> bikes) {
        this.bikes = bikes;
    }
}
