package pg.eti.kask.jee.quickr.model.order;

import lombok.*;
import pg.eti.kask.jee.quickr.model.venue.VenueModel;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class OrderCreateModel {
    private UUID id;
    private Double price;
    private VenueModel venue;
    private UUID userId;
    private LocalDate orderDate;
}
