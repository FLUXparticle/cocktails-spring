package org.example.coktail.controller;

import org.example.coktail.repository.*;
import org.example.coktail.service.*;
import org.springframework.data.domain.*;
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
    public Page<Cocktail> getCocktails(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int pageSize) {
        return cocktailService.getCocktailsAsPage(page, pageSize);
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
