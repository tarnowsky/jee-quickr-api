package pg.eti.kask.jee.quickr.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import lombok.NoArgsConstructor;
import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.controller.exception.OrderNotFoundException;
import pg.eti.kask.jee.quickr.entity.User;
import pg.eti.kask.jee.quickr.entity.Venue;
import pg.eti.kask.jee.quickr.repository.api.OrderRepository;
import pg.eti.kask.jee.quickr.repository.api.VenueRepository;
import pg.eti.kask.jee.quickr.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final VenueRepository venueRepository;

    @Inject
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, VenueRepository venueRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.venueRepository = venueRepository;
    }

    public Optional<List<Order>> findAllByUser(UUID userId) {
        return userRepository.findById(userId)
                .map(orderRepository::findAllByUser);
    }

    public Optional<List<Order>> findAllByVenue(UUID venueId) {
        return venueRepository.findById(venueId)
                .map(orderRepository::findAllByVenue);

    }

    public List<Order> findAll(User user) {
        return orderRepository.findAllByUser(user);
    }

    public List<Order> findAll(Venue venue) {
        return orderRepository.findAllByVenue(venue);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> find(User user, UUID id) {
        return orderRepository.findByIdAndUser(id, user);
    }

    public void create(Order firearm) {
        if (orderRepository.findById(firearm.getId()).isPresent()) {
            throw new IllegalArgumentException("Order already exists.");
        }
        if (venueRepository.findById(firearm.getVenue().getId()).isEmpty()) {
            throw new IllegalArgumentException("Venue does not exists.");
        }
        orderRepository.create(firearm);
    }

    public void update(Order order) {
        orderRepository.update(order);
    }

    public void delete(UUID id) {
        orderRepository.findById(id).ifPresent(orderRepository::delete);
    }

}
