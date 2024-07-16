package aston.bootcamp.dto;

import java.util.Objects;

public class DealershipIncomingDto {
    private String city;
    private String street;
    private Long houseNum;

    public DealershipIncomingDto() {
    }

    public DealershipIncomingDto(String city, String street, Long houseNum) {
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
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
        DealershipIncomingDto that = (DealershipIncomingDto) o;
        return Objects.equals(city, that.city) && Objects.equals(street, that.street) && Objects.equals(houseNum, that.houseNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, houseNum);
    }
}
