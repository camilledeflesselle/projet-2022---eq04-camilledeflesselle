package ca.ulaval.glo4002.cafe.ui.rest.validators.menu;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.menu.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.MenuItemDTO;
import jakarta.ws.rs.BadRequestException;
import java.util.List;

public class MenuItemAssembler {
    private final IMenuItemRepository menu;
    private final RecipeValidator recipeValidator;

    public MenuItemAssembler(IMenuItemRepository menuItemRepository) {
        this.menu = menuItemRepository;
        this.recipeValidator = new RecipeValidator();
    }


    public MenuItem menuItemDTOToMenuItem(MenuItemDTO menuItemDTO) {
        this.validateName(menuItemDTO.getName());
        this.validateCost(menuItemDTO.getCost());
        MenuItemId menuItemId = new MenuItemId(menuItemDTO.getName(), true);
        Amount cost = new Amount(menuItemDTO.getCost());
        return new MenuItem(menuItemId, cost);
    }

    public Recipe menuItemDTOToRecipe(MenuItemDTO menuItemDTO) {
        MenuItemId menuItemId = new MenuItemId(menuItemDTO.getName(), true);
        List<Ingredient> ingredients = this.recipeValidator.recipeDTOToListIngredients(menuItemDTO.getIngredients());
        return new Recipe(menuItemId, ingredients);
    }

    private void validateCost(float cost) {
        if (cost < 0) {
            throw new BadRequestException("Coffee price cannot be negative.");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BadRequestException("Coffee name cannot be empty.");
        }
        if (menu.findMenuItemByName(name) != null) {
            throw new BadRequestException("Coffee name already exists.");
        }
    }
}
