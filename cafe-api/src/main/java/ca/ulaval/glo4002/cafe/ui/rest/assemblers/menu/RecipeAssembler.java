package ca.ulaval.glo4002.cafe.ui.rest.assemblers.menu;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientInLes4Fees;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredients;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.RecipeDTO;
import jakarta.ws.rs.BadRequestException;

import java.util.Arrays;
import java.util.Map;

public class RecipeAssembler {

    public RecipeAssembler() {
    }

    public Ingredients assembleRecipeDTOToRecipe(RecipeDTO recipeDTO) {
        Ingredients ingredientsRecipe = new Ingredients();
        Map<String, Integer> ingredients = recipeDTO.getIngredients();

        if (ingredients.isEmpty()) {
            throw new BadRequestException("Coffee ingredients cannot be empty.");
        }


        if (ingredients.containsValue(-1)) {
            throw new BadRequestException("Coffee ingredient quantity cannot be empty or negative.");
        }

        Arrays.stream(IngredientInLes4Fees.values()).map(IngredientInLes4Fees::getId).forEach(ingredientId -> {
            if (!ingredients.containsKey(ingredientId.getName())) {
                throw new BadRequestException("Coffee ingredient " + ingredientId.getName() + " is missing.");
            }
            ingredientsRecipe.addIngredient(ingredientId, ingredients.get(ingredientId.getName()));
        });
        return ingredientsRecipe;
    }
}
