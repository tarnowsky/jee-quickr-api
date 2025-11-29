package pg.eti.kask.jee.quickr.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderFilter implements Serializable {
    private String name;
    private Double minPrice;
    private Double maxPrice;
    private Integer minItemCount;
    private Integer maxItemCount;
    private UUID venueId; // Optional context
}
