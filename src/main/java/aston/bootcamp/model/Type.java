package aston.bootcamp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "types")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "type", fetch = FetchType.EAGER)
    private List<Bike> bikes;

    public Type() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Type{" +
               "id=" + id +
               ", type='" + type + '}';
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
            newBike.setBrand(new Brand(bike.getBrand().getId(), bike.getBrand().getBrand(), new ArrayList<>()));
            newBike.setType(new Type(bike.getType().getId(), bike.getType().getType(), new ArrayList<>()));
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

    public Type(Long id, String type, List<Bike> bikes) {
        this.id = id;
        this.type = type;
        this.bikes = bikes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type1 = (Type) o;
        return Objects.equals(id, type1.id) && Objects.equals(type, type1.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
