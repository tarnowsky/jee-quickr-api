package pg.eti.kask.jee.quickr.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.entity.Venue;
import pg.eti.kask.jee.quickr.repository.api.OrderRepository;
import pg.eti.kask.jee.quickr.repository.api.VenueRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class VenueService {

    private final VenueRepository venueRepository;
    private final OrderRepository orderRepository;

    @Inject
    public VenueService(VenueRepository venueRepository, OrderRepository orderRepository) {
        this.venueRepository = venueRepository;
        this.orderRepository = orderRepository;
    }

    public List<Venue> findAll() {
        return venueRepository.findAll();
    }

    public Optional<Venue> findById(@NonNull UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Venue ID cannot be null");
        }
        return venueRepository.findById(id);
    }

    public void create(@NonNull Venue venue) {
        if (venue == null) {
            throw new IllegalArgumentException("Venue cannot be null");
        }
        venueRepository.create(venue);
    }

    public void update(@NonNull Venue venue) {
        if (venue == null) {
            throw new IllegalArgumentException("Venue cannot be null");
        }
        venueRepository.update(venue);
    }

    public void delete(@NonNull UUID id) {
        venueRepository.findById(id).ifPresent(venueRepository::delete);
    }

}
