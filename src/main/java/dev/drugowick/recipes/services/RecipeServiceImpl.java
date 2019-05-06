package dev.drugowick.recipes.services;

import dev.drugowick.recipes.domain.Recipe;
import dev.drugowick.recipes.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe not found.");
        }

        return recipeOptional.get();
    }


}
