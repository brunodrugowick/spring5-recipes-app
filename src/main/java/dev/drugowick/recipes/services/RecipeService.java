package dev.drugowick.recipes.services;

import dev.drugowick.recipes.converters.commands.RecipeCommand;
import dev.drugowick.recipes.domain.Recipe;

public interface RecipeService {
    Iterable<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
}
