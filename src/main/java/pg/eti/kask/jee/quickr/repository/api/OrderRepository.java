package pg.eti.kask.jee.quickr.repository.api;

import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.entity.Venue;
import pg.eti.kask.jee.quickr.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends Repository<Order, UUID> {
    List<Order> findAllByUser(User user);
    List<Order> findAllByVenue(Venue venue);
    Optional<Order> findByIdAndUser(UUID id, User user);
}
