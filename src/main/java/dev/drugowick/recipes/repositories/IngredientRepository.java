package dev.drugowick.recipes.repositories;

import dev.drugowick.recipes.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

}
