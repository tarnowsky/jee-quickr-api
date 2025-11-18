package pg.eti.kask.jee.quickr.dto.venue.function;

import pg.eti.kask.jee.quickr.dto.venue.GetVenueResponse;
import pg.eti.kask.jee.quickr.entity.Venue;

import java.util.function.Function;

public class ReturnVenueFunction implements Function<Venue, GetVenueResponse> {

    @Override
    public GetVenueResponse apply(Venue venue) {
        return GetVenueResponse.builder()
                .id(venue.getId())
                .name(venue.getName())
                .venueCategory(venue.getVenueCategory().toString())
                .build();
    }
}
