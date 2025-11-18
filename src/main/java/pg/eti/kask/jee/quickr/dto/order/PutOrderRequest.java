package pg.eti.kask.jee.quickr.dto.order;

import lombok.*;

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
