package aston.bootcamp.dto;

import aston.bootcamp.model.Bike;

import java.util.List;

public class BrandOutgoingDto {
    private Long id;
    private String brand;
    private List<BikeOutgoingDto> bikes;

    public BrandOutgoingDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<BikeOutgoingDto> getBikes() {
        return bikes;
    }

    public void setBikes(List<BikeOutgoingDto> bikes) {
        this.bikes = bikes;
    }

    public BrandOutgoingDto(Long id, String brand, List<BikeOutgoingDto> bikes) {
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
}
