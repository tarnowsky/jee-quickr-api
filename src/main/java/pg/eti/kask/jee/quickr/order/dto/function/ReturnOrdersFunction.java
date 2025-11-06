package pg.eti.kask.jee.quickr.order.dto.function;

import pg.eti.kask.jee.quickr.order.dto.GetOrdersResponse;
import pg.eti.kask.jee.quickr.order.entity.Order;

import java.util.List;
import java.util.function.Function;

public class ReturnOrdersFunction implements Function<List<Order>, GetOrdersResponse> {

    @Override
    public GetOrdersResponse apply(List<Order> orders) {
        return GetOrdersResponse.builder()
                .orders(orders.stream()
                        .map(order -> GetOrdersResponse.Order.builder()
                                .id(order.getId())
                                .price(order.getPrice())
                                .build())
                        .toList())
                .build();
    }
}
