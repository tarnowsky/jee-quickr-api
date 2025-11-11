package pg.eti.kask.jee.quickr.order.entity;

import jakarta.persistence.*;
import lombok.*;
import pg.eti.kask.jee.quickr.user.entity.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    private UUID id;
    private Double price;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "order_date")
    private LocalDate orderDate;
}
