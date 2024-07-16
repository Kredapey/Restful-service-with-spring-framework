package aston.bootcamp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "brand")
    private String brand;
    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER)
    private List<Bike> bikes;

    public Brand(Long id, String brand, List<Bike> bikes) {
        this.id = id;
        this.brand = brand;
        this.bikes = bikes;
    }

    public List<Bike> getBikes() {
        List<Bike> result = new ArrayList<>(bikes.size());
        for (Bike bike : bikes) {
            Bike newBike = new Bike(bike.getId(),
                    bike.getType(),
                    bike.getBrand(),
                    bike.getModel(),
                    bike.getCost(),
                    new ArrayList<>());
            newBike.setType(new Type(bike.getType().getId(), bike.getType().getType(), new ArrayList<>()));
            newBike.setBrand(new Brand(bike.getBrand().getId(), bike.getBrand().getBrand(), new ArrayList<>()));
            result.add(newBike);
        }
        return result;
    }

    public void setBikes(List<Bike> bikes) {
        if (bikes == null) {
            bikes = new ArrayList<>();
        }
        this.bikes = bikes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand1 = (Brand) o;
        return Objects.equals(id, brand1.id) && Objects.equals(brand, brand1.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand);
    }

    public Brand() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String name) {
        this.brand = name;
    }

    @Override
    public String toString() {
        return "Brand{" +
               "id=" + id +
               ", brand='" + brand + '\'' +
               '}';
    }
}
