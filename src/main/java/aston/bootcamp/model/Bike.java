package aston.bootcamp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bikes")
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private Type type;
    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;
    @Column(name = "model")
    private String model;
    @Column(name = "cost")
    private Long cost;
    @ManyToMany(mappedBy = "bikes",
    fetch = FetchType.EAGER)
    List<Dealership> dealerships;

    public Bike(Long id, Type type, Brand brand, String model, Long cost, List<Dealership> dealerships) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.cost = cost;
        this.dealerships = dealerships;
    }

    public Bike() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return new Type(type.getId(), type.getType(), new ArrayList<>());
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Brand getBrand() {
        return new Brand(brand.getId(), brand.getBrand(), new ArrayList<>());
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public List<Dealership> getDealerships() {
        List<Dealership> result = new ArrayList<>(dealerships.size());
        for (Dealership dealership : dealerships) {
            Dealership newDealership = new Dealership(dealership.getId(),
                    dealership.getCity(),
                    dealership.getStreet(),
                    dealership.getHouseNum(),
                    new ArrayList<>());
            result.add(newDealership);
        }
        return result;
    }

    public void setDealerships(List<Dealership> dealerships) {
        if (dealerships == null) {
            dealerships = new ArrayList<>();
        }
        this.dealerships = dealerships;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bike bike = (Bike) o;
        return Objects.equals(id, bike.id) && Objects.equals(type, bike.type) && Objects.equals(brand, bike.brand) && Objects.equals(model, bike.model) && Objects.equals(cost, bike.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, brand, model, cost);
    }

    @Override
    public String toString() {
        return "Bike{" +
               "id=" + id +
               ", type=" + type +
               ", brand=" + brand +
               ", model='" + model + '\'' +
               ", cost=" + cost +'}';
    }
}
