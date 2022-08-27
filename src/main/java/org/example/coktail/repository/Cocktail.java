package org.example.coktail.repository;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Entity
@JsonInclude(value = CUSTOM, valueFilter = LazyFilter.class)
public class Cocktail implements Comparable<Cocktail> {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn // (name="cocktail_id")
    private Collection<Instruction> instructions;

    protected Cocktail() {
        // Required by JAXB
    }

    public Cocktail(String name, List<Instruction> instructions) {
        this.name = name;
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "Cocktail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructions=" + instructions +
                '}';
    }

    @Override
    public int compareTo(Cocktail other) {
        return name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cocktail cocktail = (Cocktail) o;
        return id.equals(cocktail.id) && name.equals(cocktail.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Instruction> getInstructions() {
        return instructions;
    }

}
