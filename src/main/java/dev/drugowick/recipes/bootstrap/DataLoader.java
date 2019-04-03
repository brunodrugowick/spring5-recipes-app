package dev.drugowick.recipes.bootstrap;

import dev.drugowick.recipes.domain.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${petclinic.devmode:#{'0'}}")
    private String devMode;

    @Value("${petclinic.devmode.password:#{'xibanga'}}")
    private String devPass;

    @Override
    public void run(String... args) throws Exception {

        if (devMode.equals("1") && devPass.equals("aiowas")) {
            System.out.println("DEVMODE: Sample data will be created.");
            loadData();
        }
    }

    private void loadData() {
        createAndSaveRecipe("Big Mac", 30, 5, 1);
    }

    private Recipe createAndSaveRecipe(String description, int prepTime, int cookTime, int servings) {
        Recipe recipe = new Recipe();
        recipe.setDescription(description);
        recipe.setPrepTime(prepTime);
        recipe.setCookTime(cookTime);
        recipe.setServings(servings);
        recipe.setDirections("A long text explaining");

        return recipe;
    }
}
