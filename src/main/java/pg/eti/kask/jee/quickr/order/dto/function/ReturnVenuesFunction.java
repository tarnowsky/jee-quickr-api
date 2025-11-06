package pg.eti.kask.jee.quickr.order.dto.function;

import pg.eti.kask.jee.quickr.order.dto.GetVenuesResponse;
import pg.eti.kask.jee.quickr.order.entity.Venue;

import java.util.List;
import java.util.function.Function;

public class ReturnVenuesFunction implements Function<List<Venue>, GetVenuesResponse> {

    @Override
    public GetVenuesResponse apply(List<Venue> venues) {
        return GetVenuesResponse.builder()
                .venues(venues.stream()
                        .map(venue -> GetVenuesResponse.Venue.builder()
                                .id(venue.getId())
                                .name(venue.getName())
                                .build())
                        .toList())
                .build();
    }
}
