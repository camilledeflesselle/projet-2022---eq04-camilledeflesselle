package ca.ulaval.glo4002.cafe.domain.recipe;

public interface IRecipeRepository {
    Recipe findRecipeByName(String recipeName);

    void addRecipe(Recipe recipe);
}
