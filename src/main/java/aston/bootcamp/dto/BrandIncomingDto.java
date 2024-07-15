package aston.bootcamp.dto;

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
}
