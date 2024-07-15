package aston.bootcamp.dto;

import aston.bootcamp.model.Bike;

import java.util.List;

public class TypeUpdateDto {
    private Long id;
    private String type;
    List<BikeUpdateDto> bikes;

    public TypeUpdateDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<BikeUpdateDto> getBikes() {
        return bikes;
    }

    public void setBikes(List<BikeUpdateDto> bikes) {
        this.bikes = bikes;
    }

    public TypeUpdateDto(Long id, String type, List<BikeUpdateDto> bikes) {
        this.id = id;
        this.type = type;
        this.bikes = bikes;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
