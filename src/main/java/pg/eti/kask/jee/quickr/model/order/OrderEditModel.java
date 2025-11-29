package pg.eti.kask.jee.quickr.model.order;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class OrderEditModel {
    private Double price;
    private LocalDate orderDate;
    private Long version;
    // private UUID userId;
    // private UUID venueId;
}
