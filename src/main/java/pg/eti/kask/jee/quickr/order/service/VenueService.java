package pg.eti.kask.jee.quickr.order.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pg.eti.kask.jee.quickr.controller.servlet.exception.IdNotUniqueException;
import pg.eti.kask.jee.quickr.controller.servlet.exception.NotFoundException;
import pg.eti.kask.jee.quickr.order.entity.Venue;
import pg.eti.kask.jee.quickr.order.repository.api.VenueRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class VenueService {

    private final VenueRepository repository;

    @Inject
    public VenueService(VenueRepository repository) {
        this.repository = repository;
    }

    public Venue find(@NonNull UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Venue with a given id %s not found".formatted(id)));
    }

    public Venue create(@NonNull Venue user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("Venue id cannot be null");
        }

        return repository.create(user)
                .orElseThrow(() -> new IdNotUniqueException("Venue with a given id %s exists in the datastore"
                    .formatted(user.getId())));
    }

    public List<Venue> findAll() {
        return repository.findAll();
    }

    public Venue update(@NonNull Venue user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("Venue id cannot be null");
        }

        return repository.update(user)
                .orElseThrow(() -> new NotFoundException("Venue id %s not found in datastore".formatted(user.getId())));
    }

    public Venue delete(@NonNull UUID id) {
        return repository.delete(id)
                .orElseThrow(() -> new NotFoundException("Venue id %s not found in datastore".formatted(id)));
    }
}
