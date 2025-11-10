package pg.eti.kask.jee.quickr.order.model.function;

import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.model.OrderEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class OrderToEditModelFunction implements Function<Order, OrderEditModel>, Serializable {

    @Override
    public OrderEditModel apply(Order order) {
        return OrderEditModel.builder()
                .price(order.getPrice())
                .orderDate(order.getOrderDate())
//                .userId(order.getUser().getId())
//                .venueId(order.getVenue().getId())
                .build();
    }
}
