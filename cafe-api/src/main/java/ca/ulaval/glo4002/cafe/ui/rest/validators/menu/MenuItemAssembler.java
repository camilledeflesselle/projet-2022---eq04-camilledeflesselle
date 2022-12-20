package ca.ulaval.glo4002.cafe.ui.rest.validators.menu;

import ca.ulaval.glo4002.cafe.application.inventory.IngredientType;
import ca.ulaval.glo4002.cafe.domain.bill.Amount;
import ca.ulaval.glo4002.cafe.domain.inventory.Ingredient;
import ca.ulaval.glo4002.cafe.domain.inventory.IngredientId;
import ca.ulaval.glo4002.cafe.domain.menu.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemId;
import ca.ulaval.glo4002.cafe.domain.recipe.Recipe;
import ca.ulaval.glo4002.cafe.ui.rest.DTO.MenuItemDTO;
import jakarta.ws.rs.BadRequestException;

import java.util.Arrays;
import java.util.List;

public class MenuItemAssembler {
    private final IMenuItemRepository menu;

    public MenuItemAssembler(IMenuItemRepository menuItemRepository) {
        this.menu = menuItemRepository;
    }

    public void validate(MenuItemDTO menuItemDTO) {
        this.validateName(menuItemDTO.getName());

        if (menuItemDTO.getCost() < 0) {
            throw new BadRequestException("Coffee price cannot be negative.");
        }

        this.validateRecipe(menuItemDTO);
    }


    public MenuItem menuItemDTOToMenuItem(MenuItemDTO menuItemDTO) {
        MenuItemId menuItemId = new MenuItemId(menuItemDTO.getName());
        Amount cost = new Amount(menuItemDTO.getCost());
        return new MenuItem(menuItemId, cost);
    }

    public Recipe menuItemDTOToRecipe(MenuItemDTO menuItemDTO) {
        MenuItemId menuItemId = new MenuItemId(menuItemDTO.getName());
        List<Ingredient> ingredients = menuItemDTO.getIngredients().entrySet().stream()
                .map(entry -> new Ingredient(new IngredientId(entry.getKey()), entry.getValue()))
                .toList();
        return new Recipe(menuItemId, ingredients);
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BadRequestException("Coffee name cannot be empty.");
        }
        if (menu.findMenuItemByName(name) != null) {
            throw new BadRequestException("Coffee name already exists.");
        }

    }

    private void validateRecipe(MenuItemDTO menuItemDTO) {
        if (menuItemDTO.getIngredients() == null || menuItemDTO.getIngredients().isEmpty()) {
            throw new BadRequestException("Coffee ingredients cannot be empty.");
        }


        if (menuItemDTO.getIngredients().containsValue(-1)) {
            throw new BadRequestException("Coffee ingredient quantity cannot be empty or negative.");
        }

        if(!menuItemDTO.getIngredients().keySet().stream().allMatch(IngredientType::contains)) {
            throw new BadRequestException("Coffee ingredient name is not valid.");
        }

        Arrays.stream(IngredientType.values()).map(IngredientType::getLabel).forEach(ingredient -> {
            if (!menuItemDTO.getIngredients().containsKey(ingredient)) {
                throw new BadRequestException("Coffee ingredient " + ingredient + " is missing.");
            }
        });
    }


}
