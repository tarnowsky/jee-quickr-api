package pg.eti.kask.jee.quickr.user.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pg.eti.kask.jee.quickr.crypto.component.Pbkdf2PasswordHash;
import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.repository.api.OrderRepository;
import pg.eti.kask.jee.quickr.user.entity.User;
import pg.eti.kask.jee.quickr.user.repository.api.UserRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public UserService(UserRepository userRepository, OrderRepository orderRepository, Pbkdf2PasswordHash passwordHash) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.passwordHash = passwordHash;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User find(@NonNull UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with a given id %s not found".formatted(id)));
    }

    public User find(@NonNull String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException("User with a given login %s not found".formatted(login)));
    }

    @Transactional
    public User create(@NonNull User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        return userRepository.create(user)
                .orElseThrow(() -> new BadRequestException("User with a given id %s exists in the datastore"
                        .formatted(user.getId())));
    }

    @Transactional
    public User update(@NonNull User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }

        return userRepository.update(user)
                .orElseThrow(() -> new NotFoundException("User id %s not found in datastore".formatted(user.getId())));
    }

    @Transactional
    public User delete(@NonNull UUID id) {
        return userRepository.delete(id)
                .orElseThrow(() -> new NotFoundException("User id %s not found in datastore".formatted(id)));
    }

    public boolean verify(@NonNull String login, @NonNull String password) {
        return passwordHash.verify(password.toCharArray(), find(login).getPassword());
    }

    public List<Order> findAllOrdersByUserId(@NonNull UUID id) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getUser().getId().equals(id))
                .toList();
    }

    public User updatePassword(@NonNull User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        return userRepository.update(user)
                .orElseThrow(() -> new NotFoundException("User id %s not found in datastore".formatted(user.getId())));
    }
}
