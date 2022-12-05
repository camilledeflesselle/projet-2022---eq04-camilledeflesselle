package ca.ulaval.glo4002.cafe.application.bill;

import ca.ulaval.glo4002.cafe.domain.bill.Bill;
import ca.ulaval.glo4002.cafe.domain.bill.IBillRepository;
import ca.ulaval.glo4002.cafe.domain.bill.ITaxesRepository;
import ca.ulaval.glo4002.cafe.domain.bill.TaxRate;
import ca.ulaval.glo4002.cafe.domain.bill.TipRate;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.order.Order;

import java.util.Objects;

public class BillService {
    private final BillFactory billFactory;
    private final IBillRepository billRepository;
    private final ITaxesRepository taxesRepository;
    private final String defaultCountry = "None";
    private TaxRate taxRate = new TaxRate(0f);
    private String country;
    private String province;
    private String state;
    private String areaName;
    private TipRate groupTipRate;

    public BillService(BillFactory billFactory, IBillRepository billRepository, ITaxesRepository taxesRepositoryInMemory) {
        this.billFactory = billFactory;
        this.billRepository = billRepository;
        this.taxesRepository = taxesRepositoryInMemory;
        this.country = this.defaultCountry;
        this.groupTipRate = new TipRate(0.15f);
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
