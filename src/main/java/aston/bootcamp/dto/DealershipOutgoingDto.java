package aston.bootcamp.dto;


import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DealershipOutgoingDto that = (DealershipOutgoingDto) o;
        return Objects.equals(id, that.id) && Objects.equals(city, that.city) && Objects.equals(street, that.street) && Objects.equals(houseNum, that.houseNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, street, houseNum);
    }
}
