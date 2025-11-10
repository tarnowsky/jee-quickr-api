package pg.eti.kask.jee.quickr.order.model;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class OrderEditModel {
    private Double price;
    private LocalDate orderDate;
//    private UUID userId;
//    private UUID venueId;
}
