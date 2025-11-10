package pg.eti.kask.jee.quickr.order.model.function;

import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.model.OrdersModel;

import java.util.List;
import java.util.function.Function;

public class OrdersToModelFunction implements Function<List<Order>, OrdersModel> {

    @Override
    public OrdersModel apply(List<Order> orders) {
        return OrdersModel.builder()
                .orders(orders.stream()
                        .map(order -> OrdersModel.Order.builder()
                                .id(order.getId())
                                .price(order.getPrice())
                                .build())
                        .toList())
                .build();
    }
}
