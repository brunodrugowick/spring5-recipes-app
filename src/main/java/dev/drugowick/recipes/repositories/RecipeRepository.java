package dev.drugowick.recipes.repositories;

import dev.drugowick.recipes.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    @Override
    Optional<Recipe> findById(Long aLong);

    @Override
    Iterable<Recipe> findAll();
}
