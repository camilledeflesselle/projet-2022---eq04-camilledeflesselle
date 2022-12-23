package ca.ulaval.glo4002.cafe.application.cooking;

import ca.ulaval.glo4002.cafe.application.menu.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RecipeFactoryTest {


    private RecipeFactory recipeFactory;

    @BeforeEach
    public void initializeRepository(){
        recipeFactory = new RecipeFactory();
    }

    @Test
    public void whenCreateRecipe_thenRecipeIsCreated() {
        Recipe recipe = recipeFactory.createRecipe(CoffeeType.Americano);

        assertNotNull(recipe);
    }

    @Test
    public void whenCreateRecipeForCoffeeInLes4Fees_thenAllRecipesAreNotNull() {
        Arrays.stream(CoffeeType.values()).forEach(coffeeType -> {
            Recipe recipe = recipeFactory.createRecipe(coffeeType);

            assertNotNull(recipe);
        });
    }

    @Test
    public void whenCreateRecipeForCoffeeInLes4Fees_thenRecipeIsNotEmpty() {
        Recipe recipe = recipeFactory.createRecipe(CoffeeType.Americano);

        assertFalse(recipe.getIngredients().isEmpty());
    }

}