package pg.eti.kask.jee.quickr.model.venue;

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
        private Integer capacity;
        private String venueCategory;
        private java.time.LocalDateTime creationDateTime;
        private java.time.LocalDateTime modificationDateTime;
        private Long version;
    }

    @Singular
    List<Venue> venues;
}
