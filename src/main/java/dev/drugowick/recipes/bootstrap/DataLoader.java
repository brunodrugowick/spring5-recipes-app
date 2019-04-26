package dev.drugowick.recipes.bootstrap;

import dev.drugowick.recipes.domain.*;
import dev.drugowick.recipes.repositories.CategoryRepository;
import dev.drugowick.recipes.repositories.RecipeRepository;
import dev.drugowick.recipes.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Transactional is here to make sure that Hibernate doesn't throw an error related to the lazy initialization
 * of some relationships.
 */
@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    @Value("${petclinic.devmode:#{'0'}}")
    private String devMode;

    @Value("${petclinic.devmode.password:#{'xibanga'}}")
    private String devPass;

    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private CategoryRepository categoryRepository;

    public DataLoader(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                      CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (devMode.equals("1") && devPass.equals("aiowas")) {
            System.out.println("DEVMODE: Sample data will be created.");
            recipeRepository.saveAll(getRecipes());
        }
    }

    private Iterable<Recipe> getRecipes() {
        // Creating return variable
        Set<Recipe> recipes = new HashSet<>();

        // Getting expected Units of Measure
        UnitOfMeasure teaspoon = getUnitOfMeasure("Teaspoon");
        UnitOfMeasure tablespoon = getUnitOfMeasure("Tablespoon");
        UnitOfMeasure cup = getUnitOfMeasure("Cup");
        UnitOfMeasure pinch = getUnitOfMeasure("Pinch");
        UnitOfMeasure ml = getUnitOfMeasure("ML");
        UnitOfMeasure grams = getUnitOfMeasure("Grams");
        UnitOfMeasure units = getUnitOfMeasure("Units");

        // Getting expected Categories
        Category mexican = getCategory("Mexican");
        Category american = getCategory("American");

        // Creating Guacamole Recipe
        String guacDirections = "1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd";
        String guacNotes = "For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws";
        Recipe guacRecipe = createBasicRecipe("Perfect Guacamole", 10, 0,
                Difficulty.EASY, guacDirections, guacNotes);
        guacRecipe
                .addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), units))
                .addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teaspoon))
                .addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tablespoon))
                .addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tablespoon))
                .addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), units))
                .addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tablespoon))
                .addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), pinch))
                .addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), units));
        guacRecipe.addCategory(american);
        guacRecipe.addCategory(mexican);
        // Add to return list
        recipes.add(guacRecipe);

        // Creating Tacos Recipe
        String tacosDirections = "1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm";
        String tacosNotes = "We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ";
        Recipe tacosRecipe = createBasicRecipe("Spicy Grilled Chicken Taco", 20, 9,
                Difficulty.MODERATE, tacosDirections, tacosNotes);
        tacosRecipe
                .addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tablespoon))
                .addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaspoon))
                .addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teaspoon))
                .addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoon))
                .addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teaspoon))
                .addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), units))
                .addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), tablespoon))
                .addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tablespoon))
                .addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tablespoon))
                .addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), tablespoon))
                .addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), units))
                .addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cup))
                .addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), units))
                .addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), units))
                .addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pinch))
                .addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), units))
                .addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), units))
                .addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cup))
                .addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), units));
        tacosRecipe.addCategory(american);
        tacosRecipe.addCategory(mexican);
        // Add to return list
        recipes.add(tacosRecipe);

        return recipes;
    }

    private Recipe createBasicRecipe(String description, Integer prepTime, Integer cookTime,
                                         Difficulty difficulty, String directions, String recipeNotes) {
        Recipe recipe = new Recipe();
        recipe.setDescription(description);
        recipe.setPrepTime(prepTime);
        recipe.setCookTime(cookTime);
        recipe.setDifficulty(difficulty);
        recipe.setDirections(directions);
        recipe.setNotes(new Notes(recipeNotes));

        return recipe;
    }

    private Category getCategory(String description) {
        return categoryRepository
                .findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Category '" + description + "' not found"));
    }

    private UnitOfMeasure getUnitOfMeasure(String description) {
        return unitOfMeasureRepository
                .findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Unit of measure '" + description + "' not found"));
    }
}
