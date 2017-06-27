package com.example.reactiveapp.service;

import com.example.reactiveapp.model.RecipeModel;

/**
 * Created by mario on 27.6.2017..
 */

public class RecipeService {
    private RecipeService() {
    }

    private static RecipeService instance;

    public static RecipeService getInstance() {
        if (instance == null) {
            instance = new RecipeService();
        }
        return instance;
    }

    private RecipeModel activeRecipe;

    public RecipeModel getActiveRecipe() {
        return activeRecipe;
    }

    public void setActiveRecipe(RecipeModel activeRecipe) {
        this.activeRecipe = activeRecipe;
    }
}
