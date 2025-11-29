package pg.eti.kask.jee.quickr.model.order.function;

import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.model.order.OrderModel;

import java.io.Serializable;
import java.util.function.Function;

public class OrderToModelFunction implements Function<Order, OrderModel>, Serializable {

    @Override
    public OrderModel apply(Order order) {
        return OrderModel.builder()
                .id(order.getId().toString())
                .price(order.getPrice())
                .name(order.getName())
                .itemCount(order.getItemCount())
                .userLogin(order.getUser().getLogin())
                .venueName(order.getVenue().getName())
                .orderDate(order.getOrderDate().toString())
                .creationDateTime(order.getCreationDateTime())
                .modificationDateTime(order.getModificationDateTime())
                .version(order.getVersion())
                .build();
    }
}
