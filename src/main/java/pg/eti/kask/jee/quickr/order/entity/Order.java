package pg.eti.kask.jee.quickr.order.entity;

import lombok.*;
import pg.eti.kask.jee.quickr.user.entity.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class Order implements Serializable {
    private UUID id;
    private Double price;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Venue venue;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
    private LocalDate orderDate;
}
