package pg.eti.kask.jee.quickr.model.order.function;

import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.entity.Venue;
import pg.eti.kask.jee.quickr.model.order.OrderCreateModel;
import pg.eti.kask.jee.quickr.service.UserService;

import java.io.Serializable;
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
                .name(model.getName())
                .itemCount(model.getItemCount())
                .user(userService.findById(model.getUserId()).get())
                .orderDate(model.getOrderDate())
                .venue(Venue.builder()
                        .id(model.getVenue().getId())
                        .name(model.getVenue().getName())
                        .venueCategory(model.getVenue().getVenueCategory())
                        .build())
                .build();
    }
}
