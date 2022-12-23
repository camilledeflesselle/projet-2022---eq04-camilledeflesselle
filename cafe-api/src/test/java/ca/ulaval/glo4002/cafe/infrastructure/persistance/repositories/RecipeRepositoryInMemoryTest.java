package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.application.cooking.RecipeFactory;
import ca.ulaval.glo4002.cafe.application.inventory.IngredientInLes4Fees;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredients;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeRepositoryInMemoryTest {
    private static final boolean IS_CUSTOM = true;
    private RecipeRepositoryInMemory recipeRepositoryInMemory;

    @BeforeEach
    public void initializeRepository(){
        RecipeFactory recipeFactory = new RecipeFactory();
        recipeRepositoryInMemory = new RecipeRepositoryInMemory(recipeFactory);
    }

    @Test
    public void whenAddRecipe_thenStorageSizeIncrease() {
        Recipe recipe = new Recipe(new MenuItemId("recipe1"), new Ingredients());

        int oldSize = recipeRepositoryInMemory.getAmount();

        recipeRepositoryInMemory.save(recipe);

        assertEquals(oldSize + 1, recipeRepositoryInMemory.getAmount());
    }

    @Test
    public void whenSearchAnExistingRecipeInStorageByName_thenReturnsThisRecipe() {
        Ingredients ingredients = new Ingredients();
        ingredients.addIngredient(IngredientInLes4Fees.Espresso.getId(), 10);
        ingredients.addIngredient(IngredientInLes4Fees.Milk.getId(), 10);
        Recipe recipe = new Recipe(new MenuItemId("recipe1"), ingredients);
        recipeRepositoryInMemory.save(recipe);

        Recipe recipeFound = recipeRepositoryInMemory.findById(new MenuItemId("recipe1"));

        assertEquals(recipe, recipeFound);
    }

    @Test
    public void whenDeleteAllCustomRecipes_thenStorageSizeDecrease() {
        Ingredients ingredients = new Ingredients();
        ingredients.addIngredient(IngredientInLes4Fees.Espresso.getId(), 10);
        ingredients.addIngredient(IngredientInLes4Fees.Milk.getId(), 10);
        Recipe recipe = new Recipe(new MenuItemId("recipe1", IS_CUSTOM), ingredients);
        recipeRepositoryInMemory.save(recipe);

        int oldSize = recipeRepositoryInMemory.getAmount();

        recipeRepositoryInMemory.deleteAllCustom();

        assertEquals(oldSize - 1, recipeRepositoryInMemory.getAmount());
    }

    @Test
    public void whenDeleteAllCustomRecipes_thenStorageDoesNotContainCustomRecipes() {
        Ingredients ingredients = new Ingredients();
        ingredients.addIngredient(IngredientInLes4Fees.Espresso.getId(), 10);
        ingredients.addIngredient(IngredientInLes4Fees.Milk.getId(), 10);
        Recipe recipe = new Recipe(new MenuItemId("recipe1", IS_CUSTOM), ingredients);
        recipeRepositoryInMemory.save(recipe);

        recipeRepositoryInMemory.deleteAllCustom();

        Recipe recipeFound = recipeRepositoryInMemory.findById(new MenuItemId("recipe1", true));

        assertNull(recipeFound);
    }

    @Test
    public void whenDeleteAllCustomRecipes_thenNotRemoveNonCustomRecipes() {
        Ingredients ingredients = new Ingredients();
        ingredients.addIngredient(IngredientInLes4Fees.Espresso.getId(), 10);
        ingredients.addIngredient(IngredientInLes4Fees.Milk.getId(), 10);
        Recipe recipe = new Recipe(new MenuItemId("recipe1"), ingredients);
        recipeRepositoryInMemory.save(recipe);

        recipeRepositoryInMemory.deleteAllCustom();

        Recipe recipeFound = recipeRepositoryInMemory.findById(new MenuItemId("recipe1"));

        assertEquals(recipe, recipeFound);
    }


    @Test
    public void whenInitialized_thenStorageContainsAllLes4FeesRecipes() {
        assertNotNull(recipeRepositoryInMemory.findById(new MenuItemId("Americano")));
        assertNotNull(recipeRepositoryInMemory.findById(new MenuItemId("Cappuccino")));
        assertNotNull(recipeRepositoryInMemory.findById(new MenuItemId("Espresso")));
        assertNotNull(recipeRepositoryInMemory.findById(new MenuItemId("Latte")));
        assertNotNull(recipeRepositoryInMemory.findById(new MenuItemId("Mocha")));
    }
}