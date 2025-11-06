package pg.eti.kask.jee.quickr.order.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pg.eti.kask.jee.quickr.controller.servlet.exception.IdNotUniqueException;
import pg.eti.kask.jee.quickr.controller.servlet.exception.NotFoundException;
import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.repository.api.OrderRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class OrderService {

    private final OrderRepository repository;

    @Inject
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order find(@NonNull UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order with a given id %s not found".formatted(id)));
    }

    public Order create(@NonNull Order user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("Order id cannot be null");
        }

        return repository.create(user)
                .orElseThrow(() -> new IdNotUniqueException("Order with a given id %s exists in the datastore"
                    .formatted(user.getId())));
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order update(@NonNull Order user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("Order id cannot be null");
        }

        return repository.update(user)
                .orElseThrow(() -> new NotFoundException("Order id %s not found in datastore".formatted(user.getId())));
    }

    public Order delete(@NonNull UUID id) {
        return repository.delete(id)
                .orElseThrow(() -> new NotFoundException("Order id %s not found in datastore".formatted(id)));
    }
}
