package org.example.coktail.repository;

import org.springframework.data.repository.*;
import org.springframework.lang.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CocktailRepository extends CrudRepository<Cocktail, Long> {

    @NonNull
    Collection<Cocktail> findAll();

    Collection<Cocktail> findByNameContains(String query);

}
