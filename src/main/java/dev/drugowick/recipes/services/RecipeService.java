package dev.drugowick.recipes.services;

import dev.drugowick.recipes.converters.commands.RecipeCommand;
import dev.drugowick.recipes.domain.Recipe;

public interface RecipeService {
    Iterable<Recipe> getRecipes();

    Recipe saveRecipe(Recipe recipe);

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findCommandById(Long id);
    
    void deleteById(Long id);
}
