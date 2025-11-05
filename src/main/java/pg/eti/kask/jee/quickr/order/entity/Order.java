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
    private Venue venue;
    private User user;
    private LocalDate orderDate;
}
