package dev.drugowick.recipes.controllers;

import dev.drugowick.recipes.bootstrap.DataLoader;
import dev.drugowick.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;
    private final DataLoader dataLoader;

    public IndexController(RecipeService recipeService, DataLoader dataLoader) {
        this.recipeService = recipeService;
        this.dataLoader = dataLoader;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }

    @PostMapping("resetDemo")
    public String resetDemoDatabase () {
        dataLoader.saveAll();
        return "redirect:/";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception exception) {
        model.addAttribute("exceptionMessage", exception.getMessage());
        return "error";
    }
}
