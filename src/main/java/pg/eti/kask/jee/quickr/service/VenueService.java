package pg.eti.kask.jee.quickr.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.entity.Venue;
import pg.eti.kask.jee.quickr.entity.enums.UserRoles;
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
    private final pg.eti.kask.jee.quickr.repository.api.UserRepository userRepository;
    private final jakarta.security.enterprise.SecurityContext securityContext;

    @Inject
    public VenueService(VenueRepository venueRepository,
            pg.eti.kask.jee.quickr.repository.api.UserRepository userRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") jakarta.security.enterprise.SecurityContext securityContext) {
        this.venueRepository = venueRepository;
        this.userRepository = userRepository;
        this.securityContext = securityContext;
    }

    @RolesAllowed(UserRoles.USER)
    public List<Venue> findAll() {
        // All users (both regular and admin) see all venues
        return venueRepository.findAll();
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Venue> findById(@NonNull UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Venue ID cannot be null");
        }
        Optional<Venue> venue = venueRepository.findById(id);
        if (venue.isPresent()) {
            if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
                return venue;
            }
            pg.eti.kask.jee.quickr.entity.User user = userRepository
                    .findByLogin(securityContext.getCallerPrincipal().getName())
                    .orElseThrow(IllegalStateException::new);
            if (venue.get().getUser() != null && venue.get().getUser().getId().equals(user.getId())) {
                return venue;
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void create(@NonNull Venue venue) {
        if (venue == null) {
            throw new IllegalArgumentException("Venue cannot be null");
        }
        venueRepository.create(venue);
    }

    @RolesAllowed(UserRoles.ADMIN)
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
