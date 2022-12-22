package ca.ulaval.glo4002.cafe.ui.rest.DTO;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.LinkedHashMap;
import java.util.Map;

public class InventoryDTO {

    private final Map<String, Integer> inventory;

    public InventoryDTO() {
        this.inventory = new LinkedHashMap<>();
    }

    public InventoryDTO(Map<String, Integer> ingredients) {
        this.inventory = ingredients;
    }

    @JsonAnySetter
    void setInventoryIngredient(String ingredient, Integer quantity) {
        inventory.put(ingredient, quantity);
    }

    public Map<String, Integer> getInventory() {
        return this.inventory;
    }
}
