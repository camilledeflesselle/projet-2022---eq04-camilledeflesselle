package ca.ulaval.glo4002.cafe.domain.recipe;

public interface IRecipeRepository {
    Recipe findByName(String recipeName);

    void addRecipe(Recipe recipe);
}
