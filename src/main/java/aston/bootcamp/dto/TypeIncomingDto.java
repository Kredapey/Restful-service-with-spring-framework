package aston.bootcamp.dto;

import java.util.Objects;

public class TypeIncomingDto {
    private String type;

    public TypeIncomingDto() {
    }

    public TypeIncomingDto(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeIncomingDto that = (TypeIncomingDto) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
