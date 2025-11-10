package pg.eti.kask.jee.quickr.order.model.function;

import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.entity.Venue;
import pg.eti.kask.jee.quickr.order.model.OrderCreateModel;
import pg.eti.kask.jee.quickr.user.service.UserService;

import java.io.Serializable;
import java.util.UUID;
import java.util.function.Function;

public class ModelToOrderFunction implements Function<OrderCreateModel, Order>, Serializable {

    private final UserService userService;

    public ModelToOrderFunction(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Order apply(OrderCreateModel model) {
        return Order.builder()
                .id(model.getId())
                .price(model.getPrice())
                .user(userService.find(model.getUserId()))
                .orderDate(model.getOrderDate())
                .venue(Venue.builder()
                        .id(model.getVenue().getId())
                        .name(model.getVenue().getName())
                        .venueCategory(model.getVenue().getVenueCategory())
//                        .orders(model.getVenue().getOrders().stream()
//                                .map(orderModel -> Order.builder()
//                                        .id(UUID.fromString(orderModel.getId()))
//                                        .price(orderModel.getPrice())
//                                        .build())
//                                .toList())
                        .build())
                .build();
    }
}
