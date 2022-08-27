package org.example.coktail.service;

import org.example.coktail.repository.*;
import org.springframework.stereotype.*;
import reactor.core.publisher.*;

import java.time.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Collections.*;

@Service
public class CocktailFluxService {

    private static long DELAY = 20;

    private final CocktailRepository cocktailRepository;

    public CocktailFluxService(CocktailRepository cocktailRepository) {
        this.cocktailRepository = cocktailRepository;
    }

    public Stream<Cocktail> getAllCocktailsAsStream() {
        return delayedStream(getCocktails());
    }

    public Flux<Cocktail> getAllCocktailsAsFlux() {
        return delayedFlux(getCocktails());
    }

    public Collection<Instruction> getInstructions(long cocktailID) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return cocktailRepository.findById(cocktailID)
                .map(Cocktail::getInstructions)
                .orElse(emptyList());
    }

    public Stream<Cocktail> getAllCocktailsWithIngredientAsStream(Long ingredientsId) {
        return getAllCocktailsAsStream()
                .filter(hasIngredient(ingredientsId));
    }

    public Flux<Cocktail> getAllCocktailsWithIngredientAsFlux(Long ingredientsId) {
        return getAllCocktailsAsFlux()
                .filter(hasIngredient(ingredientsId));
    }

    private Collection<Cocktail> getCocktails() {
        return cocktailRepository.findAll();
    }

    private static Predicate<Cocktail> hasIngredient(Long ingredientID) {
        return cocktail -> cocktail.getInstructions().stream().anyMatch(usesIngredient(ingredientID));
    }

    private static Predicate<Instruction> usesIngredient(Long ingredientID) {
        return instruction -> ingredientID.equals(instruction.getIngredient().getId());
    }

    private static <T> Stream<T> delayedStream(Collection<T> collection) {
        return collection.stream().peek(cocktail -> {
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static <T> Flux<T> delayedFlux(Collection<T> collection) {
        return Flux.fromIterable(collection).delayElements(Duration.ofMillis(DELAY));
    }

}
