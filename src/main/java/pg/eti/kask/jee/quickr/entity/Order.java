package pg.eti.kask.jee.quickr.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "orders")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {

    @jakarta.validation.constraints.NotNull
    @jakarta.validation.constraints.Positive
    private Double price;

    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(min = 3, max = 30)
    private String name;

    @jakarta.validation.constraints.NotNull
    @jakarta.validation.constraints.Min(1)
    @pg.eti.kask.jee.quickr.validator.MaxItemCount(99)
    @Column(name = "item_count")
    private Integer itemCount;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @jakarta.validation.constraints.NotNull
    @Column(name = "order_date")
    private LocalDate orderDate;
}
