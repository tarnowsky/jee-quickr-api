package pg.eti.kask.jee.quickr.datastore.component;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.entity.Venue;
import pg.eti.kask.jee.quickr.serialization.component.CloningUtility;
import pg.eti.kask.jee.quickr.user.entity.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {
    private final Map<UUID, User> users = new ConcurrentHashMap<>();
    private final Map<UUID, Order> orders = new ConcurrentHashMap<>();
    private final Map<UUID, Venue> venues = new ConcurrentHashMap<>();

    private final CloningUtility cloningUtility;

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }


    // -=-=-=-=-=-=-=-=-= USERS =-=-=-=-=-=-=-=-=-=-=
    public synchronized Optional<User> findUserByLogin(String login) {
        return users.values().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }

    public synchronized List<User> findAllUsers(){
        return users.values().stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized Optional<User> findUserById(UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    public synchronized Optional<User> createUser(User value) {
        if (users.containsKey(value.getId())) {
            return Optional.empty();
        }

        User cloned = cloningUtility.clone(value);
        users.put(cloned.getId(), cloned);
        return Optional.of(cloned);
    }

    public synchronized Optional<User> updateUser(User value) {
        User cloned = cloningUtility.clone(value);
        return Optional.ofNullable(users.replace(cloned.getId(), cloned));
    }

    public synchronized Optional<User> removeUserById(UUID id) {
        return Optional.ofNullable(users.remove(id));
    }


    // -=-=-=-=-=-=-=-=-= ORDERS =-=-=-=-=-=-=-=-=-=-=
    public synchronized List<Order> findAllOrders(){
        return orders.values().stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized Optional<Order> findOrderById(UUID id) {
        return Optional.ofNullable(orders.get(id));
    }

    public synchronized Optional<Order> createOrder(Order value) {
        if (orders.containsKey(value.getId())) {
            return Optional.empty();
        }

        Order cloned = cloningUtility.clone(value);
        orders.put(cloned.getId(), cloned);
        return Optional.of(cloned);
    }

    public synchronized Optional<Order> updateOrder(Order value) {
        Order cloned = cloningUtility.clone(value);
        return Optional.ofNullable(orders.replace(cloned.getId(), cloned));
    }

    public synchronized Optional<Order> removeOrderById(UUID id) {
        return Optional.ofNullable(orders.remove(id));
    }


    // -=-=-=-=-=-=-=-=-= VENUES =-=-=-=-=-=-=-=-=-=-=
    public synchronized List<Venue> findAllVenues(){
        return venues.values().stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized Optional<Venue> findVenueById(UUID id) {
        return Optional.ofNullable(venues.get(id));
    }

    public synchronized Optional<Venue> createVenue(Venue value) {
        if (venues.containsKey(value.getId())) {
            return Optional.empty();
        }

        Venue cloned = cloningUtility.clone(value);
        venues.put(cloned.getId(), cloned);
        return Optional.of(cloned);
    }

    public synchronized Optional<Venue> updateVenue(Venue value) {
        Venue cloned = cloningUtility.clone(value);
        return Optional.ofNullable(venues.replace(cloned.getId(), cloned));
    }

    public synchronized Optional<Venue> removeVenueById(UUID id) {
        return Optional.ofNullable(venues.remove(id));
    }
}
