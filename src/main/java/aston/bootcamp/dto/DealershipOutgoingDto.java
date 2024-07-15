package aston.bootcamp.dto;

import aston.bootcamp.model.Dealership;

import java.util.List;

public class DealershipOutgoingDto {
    private Long id;
    private String city;
    private String street;
    private Long houseNum;
    private List<BikeOutgoingDto> bikes;

    public DealershipOutgoingDto() {
    }

    public DealershipOutgoingDto(Long id, String city, String street, Long houseNum, List<BikeOutgoingDto> bikes) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
        this.bikes = bikes;
    }

    public List<BikeOutgoingDto> getBikes() {
        return bikes;
    }

    public void setBikes(List<BikeOutgoingDto> bikes) {
        this.bikes = bikes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(Long houseNum) {
        this.houseNum = houseNum;
    }
}
