package aston.bootcamp.dto;

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
}
