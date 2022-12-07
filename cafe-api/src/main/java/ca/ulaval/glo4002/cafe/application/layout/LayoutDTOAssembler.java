package ca.ulaval.glo4002.cafe.application.layout;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.CubeDTO;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.LayoutDTO;
import ca.ulaval.glo4002.cafe.infrastructure.rest.DTO.SeatDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayoutDTOAssembler {
    public LayoutDTO createLayoutDTO(String name, List<Cube> cubes, List<Customer> customers) {
        Map<SeatId, CustomerId> seatedCustomers = getSeatIdToCustomerIdMap(customers);

        List<CubeDTO> cubeDTOS = new ArrayList<>();
        cubes.forEach(cube -> cubeDTOS.add(createCubeDTO(cube, seatedCustomers)));
        return new LayoutDTO(name, cubeDTOS);
    }

    private CubeDTO createCubeDTO(Cube cube, Map<SeatId, CustomerId> seatedCustomers) {
        List<SeatDTO> seatDTOS = new ArrayList<>();
        cube.getSeats().forEach(seat -> seatDTOS.add(createSeatDTO(seat, seatedCustomers)));
        return new CubeDTO(cube.getName(), seatDTOS);
    }

    private SeatDTO createSeatDTO(Seat seat, Map<SeatId, CustomerId> seatedCustomers) {
        CustomerId customerId = seatedCustomers.get(seat.getId());
        return new SeatDTO(seat.getId(), seat.getStatus(), customerId, seat.getGroupName());
    }

    private static Map<SeatId, CustomerId> getSeatIdToCustomerIdMap(List<Customer> customers) {
        Map<SeatId, CustomerId> seatIdToCustomerIdMap = new HashMap<>();
        customers.forEach(customer -> seatIdToCustomerIdMap.put(customer.getSeatId(), customer.getId()));
        return seatIdToCustomerIdMap;
    }
}
