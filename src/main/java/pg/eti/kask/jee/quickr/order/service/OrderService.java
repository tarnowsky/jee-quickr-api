package pg.eti.kask.jee.quickr.order.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.exceptions.OrderNotFoundException;
import pg.eti.kask.jee.quickr.order.repository.api.OrderRepository;
import pg.eti.kask.jee.quickr.order.repository.api.VenueRepository;
import pg.eti.kask.jee.quickr.user.repository.api.UserRepository;

import java.util.List;
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

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order find(@NonNull UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with a given id %s not found".formatted(id)));
    }

    public Order create(@NonNull Order order) {
        if (orderRepository.findById(order.getId()).isPresent()) {
            throw new IllegalArgumentException("Order already exists.");
        }

        if (userRepository.findById(order.getUser().getId()).isEmpty()) {
            throw new IllegalArgumentException("User with given id does not exist");
        }

        return orderRepository.create(order)
                .orElseThrow(() -> new BadRequestException("Order with a given id %s exists in the datastore"
                    .formatted(order.getId())));
    }

    public Order update(@NonNull Order order) {
        if (order.getId() == null) {
            throw new IllegalArgumentException("Order id cannot be null");
        }

        return orderRepository.update(order)
                .orElseThrow(() -> new OrderNotFoundException("Order id %s not found in datastore".formatted(order.getId())));
    }

    public Order delete(@NonNull UUID id) {
        return orderRepository.delete(id)
                .orElseThrow(() -> new OrderNotFoundException("Order id %s not found in datastore".formatted(id)));
    }


}
