package org.example.coktail.controller;

import org.example.coktail.repository.*;
import org.example.coktail.service.*;
import org.springframework.security.core.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final CocktailService cocktailService;

    private final FridgeService fridgeService;

    public UserController(CocktailService cocktailService, FridgeService fridgeService) {
        this.cocktailService = cocktailService;
        this.fridgeService = fridgeService;
    }

    @GetMapping("/fridge")
    public String fridge(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Collection<Ingredient> allIngredients = cocktailService.getAllIngredients();
        Set<Ingredient> fridgeIngredients = fridgeService.getFridgeIngredients();

        List<FridgeModel> fridgeModels = new ArrayList<>();
        for (Ingredient ingredient : allIngredients) {
            fridgeModels.add(new FridgeModel(ingredient, fridgeIngredients.contains(ingredient)));
        }

        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("fridgeModels", fridgeModels);
        return "fridge";
    }

    @PostMapping("/fridge")
    public String fridge(@RequestParam Long id, @RequestParam String action) {
        Ingredient ingredient = cocktailService.getIngredientWithID(id);

        switch (action) {
            case "add":
                fridgeService.addIngredient(ingredient);
                break;
            case "remove":
                fridgeService.removeIngredient(ingredient);
                break;
        }

        return "redirect:fridge";
    }

    @GetMapping("/possible")
    public String possible(Model model) {
        Collection<Cocktail> cocktails = fridgeService.getPossibleCocktails();
        model.addAttribute("cocktails", cocktails);
        return "possible";
    }

    @GetMapping("/shopping")
    public String shopping(Model model) {
        Set<Ingredient> shoppingList = fridgeService.getShoppingList();
        model.addAttribute("shoppingList", shoppingList);
        return "shopping";
    }

}
