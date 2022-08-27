package org.example.coktail.controller;

import org.example.coktail.repository.*;

public class FridgeModel {

    private final Ingredient ingredient;

    private final boolean inFridge;

    public FridgeModel(Ingredient ingredient, boolean inFridge) {
        this.ingredient = ingredient;
        this.inFridge = inFridge;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public boolean isInFridge() {
        return inFridge;
    }

}
