package pg.eti.kask.jee.quickr.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.entity.User;
import pg.eti.kask.jee.quickr.entity.Venue;
import pg.eti.kask.jee.quickr.entity.enums.UserRoles;
import pg.eti.kask.jee.quickr.repository.api.OrderRepository;
import pg.eti.kask.jee.quickr.repository.api.UserRepository;
import pg.eti.kask.jee.quickr.repository.api.VenueRepository;

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
    private final SecurityContext securityContext;

    @Inject
    public OrderService(
            OrderRepository orderRepository,
            UserRepository userRepository,
            VenueRepository venueRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.venueRepository = venueRepository;
        this.securityContext = securityContext;
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public Optional<List<Order>> findAllByUser(UUID userId) {
        return userRepository.findById(userId)
                .map(orderRepository::findAllByUser);
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public Optional<List<Order>> findAllByVenue(UUID venueId) {
        return venueRepository.findById(venueId)
                .map(orderRepository::findAllByVenue);

    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public List<Order> findAll(User user) {
        return orderRepository.findAllByUser(user);
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public List<Order> findAll(Venue venue) {
        return orderRepository.findAllByVenue(venue);
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(id);
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public Optional<Order> find(User user, UUID id) {
        return orderRepository.findByIdAndUser(id, user);
    }

    @RolesAllowed(UserRoles.USER)
    public void createForCallerPrincipal(Order order) {
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        order.setUser(user);
        create(order);
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public Optional<Order> findForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return findById(id);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return find(user, id);
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public List<Order> findAllForCallerPrincipal() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return findAll();
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return findAll(user);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void create(Order order) {
        if (orderRepository.findById(order.getId()).isPresent()) {
            throw new IllegalArgumentException("Order already exists.");
        }
        if (venueRepository.findById(order.getVenue().getId()).isEmpty()) {
            throw new IllegalArgumentException("Venue does not exists.");
        }
        orderRepository.create(order);
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public void update(Order order) {
        checkAdminRoleOrOwner(orderRepository.findById(order.getId()));
        orderRepository.update(order);
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public void delete(UUID id) {
        checkAdminRoleOrOwner(orderRepository.findById(id));
        orderRepository.findById(id).ifPresent(orderRepository::delete);
    }

    private void checkAdminRoleOrOwner(Optional<Order> order) throws EJBAccessException {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(UserRoles.USER)
                && order.isPresent()
                && order.get().getUser().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }

}
