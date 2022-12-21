package ca.ulaval.glo4002.cafe.ui.rest.validators.menu;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.RecipeDTO;
import jakarta.ws.rs.BadRequestException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RecipeValidator {

    public RecipeValidator() {
    }

    public List<Ingredient> recipeDTOToListIngredients(RecipeDTO recipeDTO) {
        List<Ingredient> ingredientsRecipe = new ArrayList<>();
        Map<String, Integer> ingredients = recipeDTO.getIngredients();

        if (ingredients.isEmpty()) {
            throw new BadRequestException("Coffee ingredients cannot be empty.");
        }


        if (ingredients.containsValue(-1)) {
            throw new BadRequestException("Coffee ingredient quantity cannot be empty or negative.");
        }

        if(!ingredients.keySet().stream().allMatch(IngredientType::contains)) {
            throw new BadRequestException("Coffee ingredient name is not valid.");
        }

        Arrays.stream(IngredientType.values()).map(IngredientType::getLabel).forEach(ingredient -> {
            if (!ingredients.containsKey(ingredient)) {
                throw new BadRequestException("Coffee ingredient " + ingredient + " is missing.");
            }
            IngredientId id = new IngredientId(ingredient);
            Ingredient ingredientRecipe = new Ingredient(id, ingredients.get(ingredient));
            ingredientsRecipe.add(ingredientRecipe);
        });
        return ingredientsRecipe;
    }
}
