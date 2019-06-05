package dev.drugowick.recipes.services;

import dev.drugowick.recipes.converters.IngredientCommandToIngredient;
import dev.drugowick.recipes.converters.IngredientToIngredientCommand;
import dev.drugowick.recipes.converters.commands.IngredientCommand;
import dev.drugowick.recipes.converters.commands.RecipeCommand;
import dev.drugowick.recipes.domain.Ingredient;
import dev.drugowick.recipes.domain.Recipe;
import dev.drugowick.recipes.repositories.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeService recipeService;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, RecipeService recipeService, IngredientCommandToIngredient ingredientCommandToIngredient, IngredientToIngredientCommand ingredientToIngredientCommand, UnitOfMeasureService unitOfMeasureService) {
        this.ingredientRepository = ingredientRepository;
        this.recipeService = recipeService;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        RecipeCommand recipeCommand = (RecipeCommand) recipeService.findCommandById(recipeId);
        Set<IngredientCommand> ingredientCommandSet = recipeCommand.getIngredients();
        for (IngredientCommand ingredientCommand : ingredientCommandSet) {
            if (ingredientCommand.getId().equals(ingredientId)) {
                return ingredientCommand;
            }
        }
        throw new RuntimeException("Ingredient not found.");
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        // John's implementation
        /*Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(!recipeOptional.isPresent()){

            //todo toss error if not found!
            log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredientSet()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(ingredientCommand.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedRecipe.getIngredientSet().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst()
                    .get());
        }*/


        /**
         * My implementation with Services
         *
         * TODO should I use other entities' repositories or their services?
         * My implementation is using Services but John's is using Repositories.
         */

        Recipe recipe = recipeService.findById(ingredientCommand.getRecipeId());

        Optional<Ingredient> optionalIngredient = recipe
                .getIngredientSet()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();

        if (optionalIngredient.isPresent()) {
            Ingredient foundIngredient = optionalIngredient.get();
            foundIngredient.setAmount(ingredientCommand.getAmount());
            foundIngredient.setDescription(ingredientCommand.getDescription());
            foundIngredient.setUnitOfMeasure(unitOfMeasureService
                    .findById(ingredientCommand.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("The UnitOfMeasure must exist before saving an Ingredient with it. The following UnitOfMeasure could not be found: " + ingredientCommand.getUnitOfMeasure().toString())));

        } else {
            Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        Recipe savedRecipe = recipeService.saveRecipe(recipe);

        return ingredientToIngredientCommand.convert(savedRecipe.getIngredientSet().stream()
                .filter(recipeIngredient -> recipeIngredient.getAmount().equals((ingredientCommand.getAmount())))
                .filter(recipeIngredient -> recipeIngredient.getDescription().equals((ingredientCommand.getDescription())))
                .filter(recipeIngredient -> recipeIngredient.getUnitOfMeasure().getId().equals((ingredientCommand.getUnitOfMeasure().getId())))
                .findFirst()
                .get());
    }

    @Override
    @Transactional
    public void deleteById(Long ingredientId, Long recipeId) {
        Recipe recipe = recipeService.findById(recipeId);
        Ingredient ingredientToDelete = recipe.getIngredientSet().stream()
                                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                                    .findFirst()
                                    .get();
        if (ingredientToDelete.getId().equals(ingredientId) && ingredientToDelete.getRecipe().getId().equals(recipeId)) {
            recipe.getIngredientSet().remove(ingredientToDelete);
            ingredientRepository.delete(ingredientToDelete);
            log.debug("Ingredient " + ingredientId + " from recipe " + recipe.getId() + " successfully removed.");
        } else {
            throw new RuntimeException("Could not find ingredient " + ingredientId + " on recipe " + recipeId + " to delete");
        }
    }
}