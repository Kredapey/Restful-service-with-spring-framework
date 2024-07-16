package aston.bootcamp.dto;


import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandUpdateDto that = (BrandUpdateDto) o;
        return Objects.equals(id, that.id) && Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand);
    }
}
