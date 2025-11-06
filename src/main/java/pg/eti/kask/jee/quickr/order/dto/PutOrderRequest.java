package pg.eti.kask.jee.quickr.order.dto;

import lombok.*;
import pg.eti.kask.jee.quickr.order.entity.Venue;
import pg.eti.kask.jee.quickr.user.entity.User;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class PutOrderRequest {
    private Double price;
    private UUID venueId;
    private UUID userId;
    private LocalDate orderDate;
}
