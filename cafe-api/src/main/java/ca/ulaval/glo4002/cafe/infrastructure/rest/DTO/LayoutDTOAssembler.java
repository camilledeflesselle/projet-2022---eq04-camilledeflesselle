package ca.ulaval.glo4002.cafe.infrastructure.rest.DTO;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.layout.Layout;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayoutDTOAssembler {
    public static LayoutDTO createLayoutDTO(Layout layout) {
        Map<SeatId, CustomerId> seatedCustomers = getSeatIdToCustomerIdMap(layout.customers().findAll());

        List<CubeDTO> cubeDTOS = new ArrayList<>();
        for (Cube cube : layout.cubes().findAll()) {
            List<SeatDTO> seatDTOS = new ArrayList<>();
            for (Seat seat : cube.getSeats()) {
                CustomerId customerId = seatedCustomers.get(seat.getId());
                SeatDTO seatDTO = new SeatDTO(seat.getId(), seat.getStatus(), customerId, seat.getGroupName());
                seatDTOS.add(seatDTO);
            }
            ;
            cubeDTOS.add(new CubeDTO(cube.getName(), seatDTOS));
        }

        return new LayoutDTO(layout.name(), cubeDTOS);
    }

    private static Map<SeatId, CustomerId> getSeatIdToCustomerIdMap(List<Customer> customers) {
        Map<SeatId, CustomerId> seatIdToCustomerIdMap = new HashMap<>();
        for (Customer customer : customers) {
            seatIdToCustomerIdMap.put(customer.getSeatId(), customer.getId());
        }
        return seatIdToCustomerIdMap;
    }
}
