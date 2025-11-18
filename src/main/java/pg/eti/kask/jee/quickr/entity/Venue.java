package pg.eti.kask.jee.quickr.entity;

import jakarta.persistence.*;
import lombok.*;
import pg.eti.kask.jee.quickr.entity.enums.VenueCategory;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Entity
@Table(name = "venues")
public class Venue implements Serializable {
    @Id
    private UUID id;
    private String name;

    @Enumerated(EnumType.STRING)
    private VenueCategory venueCategory;

    @Singular
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "venue", cascade = CascadeType.REMOVE)
    private List<Order> orders;
}
