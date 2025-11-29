package pg.eti.kask.jee.quickr.model.order.function;

import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.model.order.OrderEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateOrderWithModelFunction implements BiFunction<Order, OrderEditModel, Order>, Serializable {

    @Override
    public Order apply(Order order, OrderEditModel req) {
        return Order.builder()
                .id(order.getId())
                .price(req.getPrice() != null ? req.getPrice() : order.getPrice())
                .orderDate(req.getOrderDate() != null ? req.getOrderDate() : order.getOrderDate())
                // .user(req.getUserId() != null ? User.builder().id(req.getUserId()).build() :
                // order.getUser())
                // .venue(req.getVenueId() != null ?
                // Venue.builder().id(req.getVenueId()).build() : order.getVenue())
                // .orderDate(order.getOrderDate())
                .user(order.getUser())
                .venue(order.getVenue())
                .creationDateTime(order.getCreationDateTime())
                .version(req.getVersion())
                .build();
    }
}
