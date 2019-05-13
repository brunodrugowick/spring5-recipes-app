package dev.drugowick.recipes.controllers;

import dev.drugowick.recipes.domain.Recipe;
import dev.drugowick.recipes.repositories.CategoryRepository;
import dev.drugowick.recipes.repositories.RecipeRepository;
import dev.drugowick.recipes.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception exception) {
        model.addAttribute("exceptionMessage", exception.getMessage());
        return "error";
    }
}
