package pg.eti.kask.jee.quickr.order.dto.function;

import pg.eti.kask.jee.quickr.order.dto.GetVenueResponse;
import pg.eti.kask.jee.quickr.order.entity.Venue;

import java.util.function.Function;

public class ReturnVenueFunction implements Function<Venue, GetVenueResponse> {

    @Override
    public GetVenueResponse apply(Venue venue) {
        return GetVenueResponse.builder()
                .id(venue.getId())
                .name(venue.getName())
                .venueCategory(venue.getVenueCategory().toString())
                .orders(venue.getOrders().stream()
                        .map(order -> GetVenueResponse.Order.builder()
                                .id(order.getId())
                                .price(order.getPrice())
                                .build())
                        .toList())
                .build();
    }
}
