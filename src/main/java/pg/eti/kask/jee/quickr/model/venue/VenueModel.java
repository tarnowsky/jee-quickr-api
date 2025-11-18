package pg.eti.kask.jee.quickr.model.venue;

import lombok.*;
import pg.eti.kask.jee.quickr.entity.enums.VenueCategory;
import pg.eti.kask.jee.quickr.model.order.OrderModel;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class VenueModel {
    private UUID id;
    private String name;
    private VenueCategory venueCategory;
    @Singular
    private List<OrderModel> orders;
}
