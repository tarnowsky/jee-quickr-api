package pg.eti.kask.jee.quickr.model.order.function;

import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.model.order.OrderEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class OrderToEditModelFunction implements Function<Order, OrderEditModel>, Serializable {

    @Override
    public OrderEditModel apply(Order order) {
        return OrderEditModel.builder()
                .price(order.getPrice())
                .orderDate(order.getOrderDate())
                .version(order.getVersion())
                // .userId(order.getUser().getId())
                // .venueId(order.getVenue().getId())
                .build();
    }
}
