package ca.ulaval.glo4002.cafe.ui.rest.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuItemDTO {
    private String name;
    private RecipeDTO ingredients;
    private float cost;

    public MenuItemDTO() {}

    public MenuItemDTO(String name, RecipeDTO ingredients, float cost) {
        this.name = name;
        this.ingredients = ingredients;
        this.cost = cost;
    }

    public RecipeDTO getIngredients() {
        return this.ingredients;
    }

    public String getName() {
        return this.name;
    }

    public float getCost() {
        return this.cost;
    }
}

