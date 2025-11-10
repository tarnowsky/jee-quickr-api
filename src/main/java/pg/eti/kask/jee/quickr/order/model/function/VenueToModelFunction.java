package pg.eti.kask.jee.quickr.order.model.function;

import pg.eti.kask.jee.quickr.order.entity.Venue;
import pg.eti.kask.jee.quickr.order.model.OrderModel;
import pg.eti.kask.jee.quickr.order.model.VenueModel;

import java.io.Serializable;
import java.util.function.Function;

public class VenueToModelFunction implements Function<Venue, VenueModel>, Serializable {

    @Override
    public VenueModel apply(Venue venue) {
        return VenueModel.builder()
                .id(venue.getId())
                .name(venue.getName())
                .venueCategory(venue.getVenueCategory())
                .orders(venue.getOrders().stream()
                        .map(order -> OrderModel.builder()
                                .id(order.getId().toString())
                                .price(order.getPrice())
                                .venueName(order.getVenue().getName())
                                .userLogin(order.getUser().getLogin())
                                .orderDate(order.getOrderDate().toString())
                                .build()
                        ).toList())
                .build();
    }
}
