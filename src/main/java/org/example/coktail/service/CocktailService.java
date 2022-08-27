package org.example.coktail.service;

import org.example.coktail.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class CocktailService {

    private final CocktailRepository cocktailRepository;

    private final IngredientRepository ingredientRepository;

    public CocktailService(CocktailRepository cocktailRepository, IngredientRepository ingredientRepository) {
        this.cocktailRepository = cocktailRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public Collection<Cocktail> getAllCocktails() {
        return cocktailRepository.findAll();
    }

    public Collection<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Collection<Cocktail> getAllCocktailsWithIngredient(Long ingredientsId) {
        return getAllCocktailsWithIngredients(Collections.singleton(ingredientsId));
    }

    public Cocktail getCocktailWithID(Long id) {
        return cocktailRepository.findById(id).orElse(null);
    }

    public Ingredient getIngredientWithID(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public Collection<Cocktail> search(String query) {
        Collection<Cocktail> cocktailsWithName = cocktailRepository.findByNameContains(query);
        Collection<Ingredient> ingredientsWithName = ingredientRepository.findByNameContains(query);

        Set<Long> ingredientIDs = new HashSet<>();
        for (Ingredient ingredient : ingredientsWithName) {
            ingredientIDs.add(ingredient.getId());
        }

        SortedSet<Cocktail> result = getAllCocktailsWithIngredients(ingredientIDs);
        result.addAll(cocktailsWithName);

        return result;
    }

    private SortedSet<Cocktail> getAllCocktailsWithIngredients(Set<Long> ingredientIDs) {
        SortedSet<Cocktail> result = new TreeSet<>();

        for (Cocktail cocktail : getAllCocktails()) {
            for (Instruction instruction : cocktail.getInstructions()) {
                if (ingredientIDs.contains(instruction.getIngredient().getId())) {
                    result.add(cocktail);
                    break;
                }
            }
        }

        return result;
    }

}
