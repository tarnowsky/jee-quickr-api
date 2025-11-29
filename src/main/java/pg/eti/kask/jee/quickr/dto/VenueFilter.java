package pg.eti.kask.jee.quickr.dto;

import lombok.*;
import pg.eti.kask.jee.quickr.entity.enums.VenueCategory;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class VenueFilter implements Serializable {
    private String name;
    private VenueCategory venueCategory;
    private Integer minCapacity;
    private Integer maxCapacity;
}
