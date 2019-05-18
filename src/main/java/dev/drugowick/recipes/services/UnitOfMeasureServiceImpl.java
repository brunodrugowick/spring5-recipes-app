package dev.drugowick.recipes.services;

import dev.drugowick.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dev.drugowick.recipes.converters.commands.UnitOfMeasureCommand;
import dev.drugowick.recipes.domain.UnitOfMeasure;
import dev.drugowick.recipes.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> findAll() {

        Iterable<UnitOfMeasure> unitOfMeasureSet = unitOfMeasureRepository.findAll();
        Set<UnitOfMeasureCommand> unitOfMeasureCommandSet = new HashSet<>();
        for (UnitOfMeasure unitOfMeasure : unitOfMeasureSet) {
            unitOfMeasureCommandSet.add(unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure));
        }
        return unitOfMeasureCommandSet;
    }

    @Override
    public Optional<UnitOfMeasure> findById(Long id) {
        return unitOfMeasureRepository.findById(id);
    }

}