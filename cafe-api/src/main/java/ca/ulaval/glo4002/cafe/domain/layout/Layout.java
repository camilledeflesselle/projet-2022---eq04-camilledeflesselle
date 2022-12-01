package ca.ulaval.glo4002.cafe.domain.layout;

import ca.ulaval.glo4002.cafe.domain.cube.ICubeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.ICustomerRepository;

public record Layout(String name, ICubeRepository cubes, ICustomerRepository customers) {
}
