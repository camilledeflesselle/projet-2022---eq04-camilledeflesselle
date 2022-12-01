package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class RecipeRepositoryInMemory implements IRecipeRepository {
    private final List<Recipe> recipes;
    private final String ESPRESSO_NAME = "Espresso";
    private final String CHOCOLATE_NAME = "Chocolate";
    private final String MILK_NAME = "Milk";
    private final String WATER_NAME = "Water";

    public RecipeRepositoryInMemory() {
        this.recipes = new ArrayList<>();
        this.addRecipe(
                new Recipe("Americano",
                        Map.ofEntries(
                                entry(new Ingredient(ESPRESSO_NAME, 0), 50),
                                entry(new Ingredient(WATER_NAME, 0), 50)
                        )));
        this.addRecipe(
                new Recipe("Dark Roast",
                        Map.ofEntries(
                                entry(new Ingredient(ESPRESSO_NAME, 0), 40),
                                entry(new Ingredient(WATER_NAME, 0), 40),
                                entry(new Ingredient(CHOCOLATE_NAME, 0), 10),
                                entry(new Ingredient(MILK_NAME, 0), 10)
                        )));
        this.addRecipe(
                new Recipe("Cappuccino",
                        Map.ofEntries(
                                entry(new Ingredient(ESPRESSO_NAME, 0), 50),
                                entry(new Ingredient(WATER_NAME, 0), 40),
                                entry(new Ingredient(MILK_NAME, 0), 10)
                        )));
        this.addRecipe(
                new Recipe("Espresso",
                        Map.ofEntries(
                                entry(new Ingredient(ESPRESSO_NAME, 0), 60)
                        )));
        this.addRecipe(
                new Recipe("Flat White",
                        Map.ofEntries(
                                entry(new Ingredient(ESPRESSO_NAME, 0), 50),
                                entry(new Ingredient(MILK_NAME, 0), 50)
                        )));
        this.addRecipe(
                new Recipe("Latte",
                        Map.ofEntries(
                                entry(new Ingredient(ESPRESSO_NAME, 0), 50),
                                entry(new Ingredient(MILK_NAME, 0), 50)
                        )));
        this.addRecipe(
                new Recipe("Macchiato",
                        Map.ofEntries(
                                entry(new Ingredient(ESPRESSO_NAME, 0), 80),
                                entry(new Ingredient(MILK_NAME, 0), 20)
                        )));
        this.addRecipe(
                new Recipe("Mocha",
                        Map.ofEntries(
                                entry(new Ingredient(ESPRESSO_NAME, 0), 50),
                                entry(new Ingredient(MILK_NAME, 0), 40),
                                entry(new Ingredient(CHOCOLATE_NAME, 0), 10)
                        )));
    }

    @Override
    public Recipe findByName(String recipeName) {
        return this.recipes.stream()
                .filter(recipe -> recipe.getName().equals(recipeName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
    }

    public int getAmount() {
        return this.recipes.size();
    }
}
