package ca.ulaval.glo4002.cafe.ui.rest.DTO;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.LinkedHashMap;
import java.util.Map;

public class RecipeDTO {
    Map<String, Integer> ingredients;

    public RecipeDTO() {
        this.ingredients = new LinkedHashMap<>();
    }

    public RecipeDTO(Map<String, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    @JsonAnySetter
    void createRecipe(String ingredient, Integer quantity) {
        ingredients.put(ingredient, quantity);
    }

    public Map<String, Integer> getIngredients() {
        return this.ingredients;
    }
}
