package org.example.coktail.controller;

import org.example.coktail.repository.*;
import org.example.coktail.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.util.stream.Collectors.*;

@RestController
@RequestMapping("/rest")
public class CocktailRestController {

    private final CocktailFluxService cocktailService;

    public CocktailRestController(CocktailFluxService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping(path = "/cocktails")
    public Collection<Cocktail> getAllCocktails() {
        return cocktailService.getAllCocktailsAsStream().collect(toList());
    }

    @GetMapping("/cocktails/{id}")
    public Collection<Instruction> cocktail(@PathVariable Long id) {
        return cocktailService.getInstructions(id);
    }

    @GetMapping("/ingredients/{id}/cocktails")
    public Collection<Cocktail> ingredient(@PathVariable Long id) {
        return cocktailService.getAllCocktailsWithIngredientAsStream(id).collect(toList());
    }

}
