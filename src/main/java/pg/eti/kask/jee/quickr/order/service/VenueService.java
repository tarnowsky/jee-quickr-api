package pg.eti.kask.jee.quickr.order.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.entity.Venue;
import pg.eti.kask.jee.quickr.order.repository.api.VenueRepository;

import java.util.List;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class VenueService {

    private final VenueRepository repository;

    @Inject
    public VenueService(VenueRepository repository) {
        this.repository = repository;
    }

    public List<Venue> findAll() {
        return repository.findAll();
    }

    public Venue find(@NonNull UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Venue with a given id %s not found".formatted(id)));
    }

    public Venue create(@NonNull Venue venue) {
        if (repository.findById(venue.getId()).isPresent()) {
            throw new IllegalArgumentException("Venue already exists");
        }

        return repository.create(venue)
                .orElseThrow(() -> new BadRequestException("Venue with a given id %s exists in the datastore"
                    .formatted(venue.getId())));
    }

    public Venue update(@NonNull Venue venue) {
        if (venue.getId() == null) {
            throw new IllegalArgumentException("Venue id cannot be null");
        }

        return repository.update(venue)
                .orElseThrow(() -> new NotFoundException("Venue id %s not found in datastore".formatted(venue.getId())));
    }

    public Venue delete(@NonNull UUID id) {
        return repository.delete(id)
                .orElseThrow(() -> new NotFoundException("Venue id %s not found in datastore".formatted(id)));
    }

    public List<Order> findAllOrdersByVenueId(@NonNull UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Venue id %s not found in datastore".formatted(id)))
                .getOrders();
    }
}
