package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.inventory.IInventoryRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CoffeeTest {

    private static final String AN_ITEM_NAME = "Espresso";
    private static final Amount A_PRICE = new Amount(1.50f);
    private Coffee coffee;

    @BeforeEach
    public void createCoffee(){
        coffee = new Coffee(AN_ITEM_NAME, A_PRICE);
    }

    @Test
    void whenGetName_thenReturnsNameOfItem() {
        assertEquals(AN_ITEM_NAME, coffee.getName());
    }

    @Test
    void whenGetPrice_thenReturnsPriceOfItem() {
        assertEquals(A_PRICE, coffee.getPrice());
    }


    @Test
    void whenMakeCoffee_thenTakeIngredientsOfRecipeInStorage() {
        IInventoryRepository inventory = mock(IInventoryRepository.class);
        Recipe recipe = mock(Recipe.class);

        coffee.makeCoffee(recipe, inventory);

        verify(recipe).cookWithStorageIn(inventory);
    }
}