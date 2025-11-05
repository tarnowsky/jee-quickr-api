package pg.eti.kask.jee.quickr.order.entity;

import java.util.List;
import java.util.UUID;

public class Venue {
    private UUID id;
    private String name;
    private VenueCategory venueCategory;
    private List<Order> orders;
}
