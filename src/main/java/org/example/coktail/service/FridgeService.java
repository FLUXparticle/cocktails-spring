package org.example.coktail.service;

import org.example.coktail.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class FridgeService {

    private final CocktailService cocktailService;

    private final SortedSet<Ingredient> fridge;

    public FridgeService(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
        fridge = new TreeSet<>();
    }

    public void addIngredient(Ingredient ingredient) {
        fridge.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        fridge.remove(ingredient);
    }

    public Set<Ingredient> getFridgeIngredients() {
        return fridge;
    }

    public Collection<Cocktail> getPossibleCocktails() {
        Collection<Cocktail> allCocktails = cocktailService.getAllCocktails();
        Collection<Ingredient> fridgeIngredients = getFridgeIngredients();

        Collection<Cocktail> possibleCocktails = new ArrayList<>();

        for (Cocktail cocktail : allCocktails) {
            boolean possible = true;
            for (Instruction instruction : cocktail.getInstructions()) {
                if (!fridgeIngredients.contains(instruction.getIngredient())) {
                    possible = false;
                    break;
                }
            }
            if (possible) {
                possibleCocktails.add(cocktail);
            }
        }

        return possibleCocktails;
    }

    public Set<Ingredient> getShoppingList() {
        Collection<Ingredient> allIngredients = cocktailService.getAllIngredients();
        Collection<Ingredient> fridgeIngredients = getFridgeIngredients();

        Set<Ingredient> missing = new HashSet<>(allIngredients);
        missing.removeAll(fridgeIngredients);

        return missing;
    }

}
