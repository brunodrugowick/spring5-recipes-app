package dev.drugowick.recipes.services;

import dev.drugowick.recipes.converters.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteById(Long ingredientId, Long recipeId);
}