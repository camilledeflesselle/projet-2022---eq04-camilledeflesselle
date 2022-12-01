package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RecipeRepositoryInMemoryTest {
    private RecipeRepositoryInMemory recipeRepositoryInMemory;

    @BeforeEach
    public void initializeRepository(){
        recipeRepositoryInMemory = new RecipeRepositoryInMemory();
    }

    @Test
    public void whenAddRecipe_thenStorageSizeIncrease() {
        Recipe recipe = new Recipe("New Recipe",
                Map.ofEntries(
                        entry(new Ingredient("Espresso", 0), 50),
                        entry(new Ingredient("Milk", 0), 50)
                ));
        int oldSize = recipeRepositoryInMemory.getAmount();

        recipeRepositoryInMemory.addRecipe(recipe);

        assertEquals(oldSize + 1, recipeRepositoryInMemory.getAmount());
    }

    @Test
    public void whenSearchAnExistingRecipeInStorageByName_thenReturnsThisRecipe() {
        Recipe recipe = new Recipe("New Recipe",
                Map.ofEntries(
                        entry(new Ingredient("Espresso", 0), 50),
                        entry(new Ingredient("Milk", 0), 50)
                ));
        recipeRepositoryInMemory.addRecipe(recipe);

        Recipe recipeFound = recipeRepositoryInMemory.findByName("New Recipe");

        assertEquals(recipe, recipeFound);
    }

    @Test
    public void whenInitialized_thenStorageContainsAllLes4FeesRecipes() {
        assertNotNull(recipeRepositoryInMemory.findByName("Americano"));
        assertNotNull(recipeRepositoryInMemory.findByName("Dark Roast"));
        assertNotNull(recipeRepositoryInMemory.findByName("Cappuccino"));
        assertNotNull(recipeRepositoryInMemory.findByName("Espresso"));
        assertNotNull(recipeRepositoryInMemory.findByName("Flat White"));
        assertNotNull(recipeRepositoryInMemory.findByName("Latte"));
        assertNotNull(recipeRepositoryInMemory.findByName("Macchiato"));
        assertNotNull(recipeRepositoryInMemory.findByName("Mocha"));
    }
}