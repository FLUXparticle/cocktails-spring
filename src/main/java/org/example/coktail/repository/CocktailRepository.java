package org.example.coktail.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;
import org.springframework.lang.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    @NonNull
    List<Cocktail> findAll();

    Collection<Cocktail> findByNameContains(String query);

}
