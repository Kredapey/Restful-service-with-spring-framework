package aston.bootcamp.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dealerships")
public class Dealership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "house_num")
    private Long houseNum;
    @ManyToMany(cascade = {CascadeType.ALL},
    fetch = FetchType.EAGER)
    @JoinTable(name = "dealerships_bikes",
            joinColumns = {@JoinColumn(name = "dealership_id")},
            inverseJoinColumns = {@JoinColumn(name = "bike_id")})
    List<Bike> bikes;

    public Dealership() {
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

    public Dealership(Long id, String city, String street, Long houseNum, List<Bike> bikes) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
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
        Dealership that = (Dealership) o;
        return Objects.equals(id, that.id) && Objects.equals(city, that.city) && Objects.equals(street, that.street) && Objects.equals(houseNum, that.houseNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, street, houseNum);
    }

    @Override
    public String toString() {
        return "Dealership{" +
               "id=" + id +
               ", city='" + city + '\'' +
               ", street='" + street + '\'' +
               ", houseNum=" + houseNum + '}';
    }
}
