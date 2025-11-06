package pg.eti.kask.jee.quickr.order.dto.function;

import pg.eti.kask.jee.quickr.order.dto.GetOrderResponse;
import pg.eti.kask.jee.quickr.order.entity.Order;

import java.util.function.Function;

public class ReturnOrderFunction implements Function<Order, GetOrderResponse> {

    @Override
    public GetOrderResponse apply(Order order) {
        return GetOrderResponse.builder()
                .id(order.getId())
                .price(order.getPrice())
                .venue(GetOrderResponse.Venue.builder()
                        .id(order.getVenue().getId())
                        .name(order.getVenue().getName())
                        .build())
                .user(GetOrderResponse.User.builder()
                        .id(order.getUser().getId())
                        .login(order.getUser().getLogin())
                        .build())
                .orderDate(order.getOrderDate())
                .build();
    }
}
