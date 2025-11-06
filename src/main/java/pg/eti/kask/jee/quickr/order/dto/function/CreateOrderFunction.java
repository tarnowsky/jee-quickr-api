package pg.eti.kask.jee.quickr.order.dto.function;

import pg.eti.kask.jee.quickr.order.dto.PutOrderRequest;
import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.entity.Venue;
import pg.eti.kask.jee.quickr.user.entity.User;

import java.util.UUID;
import java.util.function.BiFunction;

public class CreateOrderFunction implements BiFunction<UUID, PutOrderRequest, Order> {

    @Override
    public Order apply(UUID id, PutOrderRequest req) {
        return Order.builder()
                .id(id)
                .price(req.getPrice())
                .venue(Venue.builder().id(req.getVenueId()).build())
                .user(User.builder().id(req.getUserId()).build())
                .orderDate(req.getOrderDate())
                .build();
    }
}
