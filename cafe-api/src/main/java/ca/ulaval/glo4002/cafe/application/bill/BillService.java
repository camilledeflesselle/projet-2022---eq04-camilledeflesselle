package ca.ulaval.glo4002.cafe.application.bill;

import ca.ulaval.glo4002.cafe.domain.bill.*;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.order.IMenuItemRepository;
import ca.ulaval.glo4002.cafe.domain.order.MenuItem;
import ca.ulaval.glo4002.cafe.domain.order.Order;
import ca.ulaval.glo4002.cafe.infrastructure.rest.validators.config.InvalidMenuOrderException;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BillService {
    private final BillFactory billFactory;
    private final IBillRepository billRepository;
    private final ITaxesRepository taxesRepository;
    private final IMenuItemRepository menuItemRepository;
    private final String defaultCountry = "None";
    private TipRate defaultGroupTipRate = new TipRate(0.15f);
    private TaxRate taxRate = new TaxRate(0f);
    private String country;
    private String province;
    private String state;
    private String areaName;
    private TipRate groupTipRate;

    public BillService(BillFactory billFactory, IBillRepository billRepository, ITaxesRepository taxesRepositoryInMemory, IMenuItemRepository menuItemRepository) {
        this.billFactory = billFactory;
        this.billRepository = billRepository;
        this.taxesRepository = taxesRepositoryInMemory;
        this.menuItemRepository = menuItemRepository;
        this.country = this.defaultCountry;
        this.groupTipRate = this.defaultGroupTipRate;
        this.areaName = "";
        this.updateTaxRate();
    }

    public void updateConfig(String country, String province, String state, TipRate groupTipRate) {
        this.country = country;
        this.province = province;
        this.state = state;
        this.groupTipRate = groupTipRate;
        if (!Objects.equals(this.country, this.defaultCountry)) {
            this.mapCalculationType();
        }
        this.updateTaxRate();
    }

    public void updateTaxRate() {
        this.taxRate = this.taxesRepository.findTaxRate(this.country, this.areaName);
    }

    public void processBillForCustomer(CustomerId customerId, Order order) {
        Bill bill = this.billFactory.createBill(order, this.taxRate);
        this.billRepository.saveBillByCustomerId(customerId, bill);
    }

    public void processBillForGroup(CustomerId customerId, Order order) {
        Bill bill = this.billFactory.createBillForGroup(order, this.taxRate, this.groupTipRate);
        this.billRepository.saveBillByCustomerId(customerId, bill);
    }

    public Bill getBillByCustomerId(CustomerId customerId) {
        return billRepository.findBillByCustomerId(customerId);
    }

    private void mapCalculationType() {
        String areaNameOfCountry = this.taxesRepository.findCalculationTypeByCountry(this.country);
        switch (areaNameOfCountry) {
            case "province" -> this.areaName = this.province;
            case "state" -> this.areaName = this.state;
            default -> this.areaName = "";
        }
    }

    public List<MenuItem> buildMenuItemListFromStr(List<String> menuItemStrList) {
        List<MenuItem> menuItemList = new ArrayList<>();
        for (String menuItemStr : menuItemStrList) {
            try {
                MenuItem menuItem = this.menuItemRepository.findMenuItemById(menuItemStr);
                menuItemList.add(menuItem);
            } catch (NotFoundException e) {
                throw new InvalidMenuOrderException();
            }
        }
        return menuItemList;
    }

    public void reset() {
        this.billRepository.deleteAll();
    }

    public TaxRate getTaxRate() {
        return this.taxRate;
    }

    public TipRate getTipRate() {
        return this.groupTipRate;
    }

    public String getAreaName() {
        return this.areaName;
    }
}
