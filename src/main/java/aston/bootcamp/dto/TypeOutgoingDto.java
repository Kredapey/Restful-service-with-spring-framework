package aston.bootcamp.dto;

import aston.bootcamp.model.Bike;

import java.util.List;

public class TypeOutgoingDto {
    private Long id;
    private String type;
    private List<BikeOutgoingDto> bikes;

    public TypeOutgoingDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<BikeOutgoingDto> getBikes() {
        return bikes;
    }

    public void setBikes(List<BikeOutgoingDto> bikes) {
        this.bikes = bikes;
    }

    public TypeOutgoingDto(Long id, String type, List<BikeOutgoingDto> bikes) {
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
