package pg.eti.kask.jee.quickr.order.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class VenuesModel {

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Builder
    public static class Venue {
        private UUID id;
        private String name;
        private String venueCategory;
    }

    @Singular
    List<Venue> venues;
}
