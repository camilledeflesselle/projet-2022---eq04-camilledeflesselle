package ca.ulaval.glo4002.cafe.ui.rest.assemblers.menu;

import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredients;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.MenuItemDTO;
import jakarta.ws.rs.BadRequestException;

public class MenuItemAssembler {
    private final MenuItemRepository menu;
    private final RecipeAssembler recipeAssembler;

    public MenuItemAssembler(MenuItemRepository menuItemRepository) {
        this.menu = menuItemRepository;
        this.recipeAssembler = new RecipeAssembler();
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
        Ingredients ingredients = this.recipeAssembler.assembleRecipeDTOToRecipe(menuItemDTO.getIngredients());
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
