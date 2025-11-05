package pg.eti.kask.jee.quickr.order.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class Venue implements Serializable {
    private UUID id;
    private String name;
    private VenueCategory venueCategory;
    @Singular
    private List<Order> orders;
}
