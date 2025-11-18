package pg.eti.kask.jee.quickr.dto.venue.function;

import pg.eti.kask.jee.quickr.dto.venue.PutVenueRequest;
import pg.eti.kask.jee.quickr.entity.Venue;
import pg.eti.kask.jee.quickr.entity.enums.VenueCategory;

import java.util.UUID;
import java.util.function.BiFunction;

public class CreateVenueFunction implements BiFunction<UUID, PutVenueRequest, Venue> {

    @Override
    public Venue apply(UUID uuid, PutVenueRequest putVenueRequest) {
        return Venue.builder()
                .id(uuid)
                .venueCategory(VenueCategory.valueOf(putVenueRequest.getVenueCategory()))
                .name(putVenueRequest.getName())
                .build();
    }
}
