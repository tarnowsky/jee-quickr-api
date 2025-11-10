package pg.eti.kask.jee.quickr.order.model.function;

import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.entity.Venue;
import pg.eti.kask.jee.quickr.order.model.OrderEditModel;
import pg.eti.kask.jee.quickr.user.entity.User;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateOrderWithModelFunction implements BiFunction<Order, OrderEditModel, Order>, Serializable {

    @Override
    public Order apply(Order order, OrderEditModel req) {
        return Order.builder()
                .id(order.getId())
                .price(req.getPrice() != null ? req.getPrice() : order.getPrice())
                .orderDate(req.getOrderDate() != null ? req.getOrderDate() : order.getOrderDate())
                .user(req.getUserId() != null ? User.builder().id(req.getUserId()).build() : order.getUser())
                .venue(req.getVenueId() != null ? Venue.builder().id(req.getVenueId()).build() : order.getVenue())
                .build();
    }
}
