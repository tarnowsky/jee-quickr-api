package pg.eti.kask.jee.quickr.model.order;

import lombok.*;

import java.time.LocalDate;

import java.io.Serializable;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class OrderEditModel implements Serializable {
    private Double price;
    private LocalDate orderDate;
    private Long version;
    // private UUID userId;
    // private UUID venueId;
}
