package pg.eti.kask.jee.quickr.model.venue.function;

import pg.eti.kask.jee.quickr.entity.Venue;
import pg.eti.kask.jee.quickr.model.venue.VenuesModel;

import java.util.List;
import java.util.function.Function;

public class VenuesToModelFunction implements Function<List<Venue>, VenuesModel> {

    @Override
    public VenuesModel apply(List<Venue> venues) {
        return VenuesModel.builder()
                .venues(venues.stream()
                        .map(venue -> VenuesModel.Venue.builder()
                                .id(venue.getId())
                                .name(venue.getName())
                                .venueCategory(venue.getVenueCategory().name())
                                .build())
                        .toList())
                .build();
    }
}
