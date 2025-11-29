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
    private Integer capacity;
    private VenueCategory venueCategory;
    private java.time.LocalDateTime creationDateTime;
    private java.time.LocalDateTime modificationDateTime;
    private Long version;
    @Singular
    private List<OrderModel> orders;
}
