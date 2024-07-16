package aston.bootcamp.dto;

import java.util.List;
import java.util.Objects;

public class TypeOutgoingDto {
    private Long id;
    private String type;
    private List<BikeOutgoingDto> bikes;

    public TypeOutgoingDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<BikeOutgoingDto> getBikes() {
        return bikes;
    }

    public void setBikes(List<BikeOutgoingDto> bikes) {
        this.bikes = bikes;
    }

    public TypeOutgoingDto(Long id, String type, List<BikeOutgoingDto> bikes) {
        this.id = id;
        this.type = type;
        this.bikes = bikes;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeOutgoingDto that = (TypeOutgoingDto) o;
        return Objects.equals(id, that.id) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
