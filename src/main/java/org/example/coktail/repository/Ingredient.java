package org.example.coktail.repository;

import javax.persistence.*;
import java.util.*;

@Entity
public class Ingredient implements Comparable<Ingredient> {

    @Id
    private Long id;

    private String name;

    protected Ingredient() {
        // Required by JAXB
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Ingredient other) {
        return name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
