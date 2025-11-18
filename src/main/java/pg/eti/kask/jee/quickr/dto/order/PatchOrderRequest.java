package pg.eti.kask.jee.quickr.dto.order;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class PatchOrderRequest {
    private Double price;
    private LocalDate orderDate;
    private UUID userId;
    private UUID venueId;
}
