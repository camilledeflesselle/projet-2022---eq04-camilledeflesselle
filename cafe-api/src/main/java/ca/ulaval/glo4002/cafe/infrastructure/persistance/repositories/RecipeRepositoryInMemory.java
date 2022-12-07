package ca.ulaval.glo4002.cafe.infrastructure.persistance.repositories;

import ca.ulaval.glo4002.cafe.application.cooking.RecipeFactory;
import ca.ulaval.glo4002.cafe.application.menu.CoffeeType;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import ca.ulaval.glo4002.cafe.domain.recipe.IRecipeRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeRepositoryInMemory implements IRecipeRepository {
    private final List<Recipe> recipes;
    private RecipeFactory recipeFactory;

    public RecipeRepositoryInMemory(RecipeFactory recipeFactory) {
        this.recipes = new ArrayList<>();
        this.recipeFactory = recipeFactory;
        this.addRecipes();
    }

    public void addRecipes() {
        Arrays.stream(CoffeeType.values()).map(recipeFactory::createRecipe).forEach(this::save);
    }

    @Override
    public Recipe findById(MenuItemId recipeName) {
        return this.recipes.stream()
                .filter(recipe -> recipe.getName().equals(recipeName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Recipe recipe) {
        this.recipes.add(recipe);
    }

    public int getAmount() {
        return this.recipes.size();
    }
}
