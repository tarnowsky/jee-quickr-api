package pg.eti.kask.jee.quickr.dto.venue;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PutVenueRequest {
    private String name;
    private String venueCategory;
}
