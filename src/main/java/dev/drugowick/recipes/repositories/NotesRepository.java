package dev.drugowick.recipes.repositories;

import dev.drugowick.recipes.domain.Notes;
import org.springframework.data.repository.CrudRepository;

public interface NotesRepository extends CrudRepository<Notes, Long> {
}
