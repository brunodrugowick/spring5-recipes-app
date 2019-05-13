package dev.drugowick.recipes.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import dev.drugowick.recipes.converters.IngredientCommandToIngredient;
import dev.drugowick.recipes.converters.commands.IngredientCommand;
import dev.drugowick.recipes.converters.commands.RecipeCommand;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeService recipeService;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeService recipeService, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeService = recipeService;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        RecipeCommand recipeCommand = (RecipeCommand) recipeService.findCommandById(recipeId);
        Set<IngredientCommand> ingredientCommandSet = recipeCommand.getIngredients();
        for (IngredientCommand ingredientCommand : ingredientCommandSet) {
            if (ingredientCommand.getId() == ingredientId) {
                return ingredientCommand;
            }
        }
        throw new RuntimeException("Ingredient not found.");
    }

}