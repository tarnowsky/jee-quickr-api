package pg.eti.kask.jee.quickr.model.venue.function;

import pg.eti.kask.jee.quickr.entity.Venue;
import pg.eti.kask.jee.quickr.model.order.OrderModel;
import pg.eti.kask.jee.quickr.model.venue.VenueModel;

import java.io.Serializable;
import java.util.function.Function;

public class VenueToModelFunction implements Function<Venue, VenueModel>, Serializable {

        @Override
        public VenueModel apply(Venue venue) {
                return VenueModel.builder()
                                .id(venue.getId())
                                .name(venue.getName())
                                .capacity(venue.getCapacity())
                                .venueCategory(venue.getVenueCategory())
                                .creationDateTime(venue.getCreationDateTime())
                                .modificationDateTime(venue.getModificationDateTime())
                                .version(venue.getVersion())
                                .orders(venue.getOrders().stream()
                                                .map(order -> OrderModel.builder()
                                                                .id(order.getId().toString())
                                                                .price(order.getPrice())
                                                                .name(order.getName())
                                                                .itemCount(order.getItemCount())
                                                                .venueName(order.getVenue().getName())
                                                                .userLogin(order.getUser().getLogin())
                                                                .orderDate(order.getOrderDate() != null
                                                                                ? order.getOrderDate().toString()
                                                                                : "")
                                                                .creationDateTime(order.getCreationDateTime())
                                                                .modificationDateTime(order.getModificationDateTime())
                                                                .version(order.getVersion())
                                                                .build())
                                                .toList())
                                .build();
        }
}
