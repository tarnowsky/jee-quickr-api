package pg.eti.kask.jee.quickr.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pg.eti.kask.jee.quickr.entity.enums.VenueCategory;

import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "venues")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Venue extends BaseEntity {
    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(min = 3, max = 50)
    private String name;

    @pg.eti.kask.jee.quickr.validator.VenueCapacity
    @jakarta.validation.constraints.NotNull
    private Integer capacity;

    @jakarta.validation.constraints.NotNull
    @Enumerated(EnumType.STRING)
    private VenueCategory venueCategory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Singular
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "venue", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Order> orders;
}
