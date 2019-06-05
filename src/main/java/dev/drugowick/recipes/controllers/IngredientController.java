package dev.drugowick.recipes.controllers;

import dev.drugowick.recipes.converters.commands.IngredientCommand;
import dev.drugowick.recipes.converters.commands.UnitOfMeasureCommand;
import dev.drugowick.recipes.services.IngredientService;
import dev.drugowick.recipes.services.RecipeService;
import dev.drugowick.recipes.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {

        if (recipeService.findCommandById(Long.parseLong(recipeId)) == null) {
            throw new RuntimeException("A valid RecipeId in necessary to create an Ingredient");
        }

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.parseLong(recipeId));
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("unitOfMeasureList", unitOfMeasureService.findAll());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId, 
                                         @PathVariable String ingredientId, Model model) {
        Long ingredientIdLong = Long.parseLong(ingredientId);                                       
        recipeService.findCommandById(Long.valueOf(recipeId)).getIngredients().forEach(ingredient -> {
            if (ingredient.getId().equals(ingredientIdLong)) {
                model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.parseLong(recipeId), 
                                                                                                 Long.parseLong(ingredientId)));
                model.addAttribute("unitOfMeasureList", unitOfMeasureService.findAll());
            }
        });                                       
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@PathVariable String recipeId,
                                         @ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() + "/show";
    }

    @DeleteMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String ingredientId,
                                   Model model) {
        ingredientService.deleteById(Long.valueOf(ingredientId), Long.valueOf(recipeId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception exception) {
        model.addAttribute("exceptionMessage", exception.getMessage());
        return "error";
    }
}