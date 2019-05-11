package dev.drugowick.recipes.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import dev.drugowick.recipes.converters.commands.CategoryCommand;
import dev.drugowick.recipes.domain.Category;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand>{

    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }

        final CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(source.getId());
        categoryCommand.setDescription(source.getDescription());
        return categoryCommand;
    }

}