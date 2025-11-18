package pg.eti.kask.jee.quickr.dto.order.function;

import pg.eti.kask.jee.quickr.dto.order.PutOrderWithVenueRequest;
import pg.eti.kask.jee.quickr.dto.util.TriFunction;
import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.entity.User;
import pg.eti.kask.jee.quickr.entity.Venue;

import java.util.UUID;

public class CreateOrderWithVenueFunction implements TriFunction<UUID, UUID, PutOrderWithVenueRequest, Order> {

    @Override
    public Order apply(UUID orderId, UUID venueId, PutOrderWithVenueRequest req) {
        return Order.builder()
                .id(orderId)
                .price(req.getPrice())
                .venue(Venue.builder().id(venueId).build())
                .user(User.builder().id(req.getUserId()).build())
                .orderDate(req.getOrderDate())
                .build();
    }
}
