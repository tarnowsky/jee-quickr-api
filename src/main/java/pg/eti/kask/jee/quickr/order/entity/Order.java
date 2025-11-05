package pg.eti.kask.jee.quickr.order.entity;

import pg.eti.kask.jee.quickr.user.entity.User;

import java.time.LocalDate;
import java.util.UUID;

public class Order {
    private UUID id;
    private Double price;
    private Venue venue;
    private User user;
    private LocalDate orderDate;
}
