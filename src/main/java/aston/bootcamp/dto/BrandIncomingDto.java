package aston.bootcamp.dto;

import java.util.Objects;

public class BrandIncomingDto {
    private String brand;

    public BrandIncomingDto() {
    }

    public BrandIncomingDto(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandIncomingDto that = (BrandIncomingDto) o;
        return Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand);
    }
}
