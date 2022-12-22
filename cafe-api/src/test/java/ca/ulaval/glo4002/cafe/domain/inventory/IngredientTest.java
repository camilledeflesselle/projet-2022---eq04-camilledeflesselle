package ca.ulaval.glo4002.cafe.domain.inventory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IngredientTest {

    @Test
    void givenAQuantity_whenUse_thenUpdateQuantity() {
        Ingredient ingredient = new Ingredient(new IngredientId("milk"), 10);
        ingredient.use(1);
        assertEquals(9, ingredient.getQuantity());
    }

    @Test
    void givenNoQuantity_whenCheckIfEnough_thenRaiseError() {
        Ingredient ingredient = new Ingredient(new IngredientId("milk"), 0);

        assertThrows(InsufficentIngredientsException.class, () -> ingredient.checkIfEnough(1));
    }
}