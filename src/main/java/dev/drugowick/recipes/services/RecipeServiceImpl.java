package dev.drugowick.recipes.services;

import dev.drugowick.recipes.domain.Recipe;
import dev.drugowick.recipes.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Iterable<Recipe> getRecipes() {
        log.debug("recipeRepository.findAll()");
        return recipeRepository.findAll();
    }
}
