package dev.drugowick.recipes.services;

import dev.drugowick.recipes.converters.commands.UnitOfMeasureCommand;
import dev.drugowick.recipes.domain.UnitOfMeasure;

import java.util.Optional;
import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> findAll();

	Optional<UnitOfMeasure> findById(Long id);
}