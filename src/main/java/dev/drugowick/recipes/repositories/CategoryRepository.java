package dev.drugowick.recipes.repositories;

import dev.drugowick.recipes.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
