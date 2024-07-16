package aston.bootcamp.dto;


import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandOutgoingDto that = (BrandOutgoingDto) o;
        return Objects.equals(id, that.id) && Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand);
    }
}
