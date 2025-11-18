package pg.eti.kask.jee.quickr.dto.venue;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class GetVenueResponse {
    private UUID id;
    private String name;
    private String venueCategory;
}
