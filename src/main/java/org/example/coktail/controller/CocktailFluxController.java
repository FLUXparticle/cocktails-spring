package org.example.coktail.controller;

import org.example.coktail.repository.*;
import org.example.coktail.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.*;

@RestController
@RequestMapping("/flux")
public class CocktailFluxController {

    private final CocktailFluxService cocktailService;

    public CocktailFluxController(CocktailFluxService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping(path = "/cocktails", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Cocktail> getAllCocktails() {
        return cocktailService.getAllCocktailsAsFlux();
    }

    @GetMapping(path = "/ingredients/{id}/cocktails", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Cocktail> ingredient(@PathVariable Long id) {
        return cocktailService.getAllCocktailsWithIngredientAsFlux(id);
    }

}
