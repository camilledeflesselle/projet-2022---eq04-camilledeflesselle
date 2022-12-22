package ca.ulaval.glo4002.cafe.ui.rest.DTO;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.io.StringWriter;
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
