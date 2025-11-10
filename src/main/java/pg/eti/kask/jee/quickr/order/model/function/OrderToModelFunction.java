package pg.eti.kask.jee.quickr.order.model.function;

import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.model.OrderModel;

import java.io.Serializable;
import java.util.function.Function;

public class OrderToModelFunction implements Function<Order, OrderModel>, Serializable {

    @Override
    public OrderModel apply(Order order) {
        return OrderModel.builder()
                .id(order.getId().toString())
                .price(order.getPrice())
                .userLogin(order.getUser().getLogin())
                .venueName(order.getVenue().getName())
                .orderDate(order.getOrderDate().toString())
                .build();
    }
}
