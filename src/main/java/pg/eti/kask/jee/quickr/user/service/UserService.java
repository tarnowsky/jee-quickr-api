package pg.eti.kask.jee.quickr.user.service;

import lombok.NonNull;
import pg.eti.kask.jee.quickr.crypto.component.Pbkdf2PasswordHash;
import pg.eti.kask.jee.quickr.user.entity.User;
import pg.eti.kask.jee.quickr.user.exceptions.UserIdNotUniqueException;
import pg.eti.kask.jee.quickr.user.exceptions.UserNotFoundException;
import pg.eti.kask.jee.quickr.user.repository.api.UserRepository;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserRepository repository;
    private final Pbkdf2PasswordHash passwordHash;

    public UserService(UserRepository repository, Pbkdf2PasswordHash passwordHash) {
        this.repository = repository;
        this.passwordHash = passwordHash;
    }

    public User find(@NonNull UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with a given id %s not found".formatted(id)));
    }

    public User find(@NonNull String login) {
        return repository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User with a given login %s not found".formatted(login)));
    }

    public User create(@NonNull User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }

        return repository.create(user)
                .orElseThrow(() -> new UserIdNotUniqueException("User with a given id %s exists in the datastore"
                    .formatted(user.getId())));
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User update(@NonNull User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }

        return repository.update(user)
                .orElseThrow(() -> new UserNotFoundException("User id %s not found in datastore".formatted(user.getId())));
    }

    public User delete(@NonNull UUID id) {
        return repository.delete(id)
                .orElseThrow(() -> new UserNotFoundException("User id %s not found in datastore".formatted(id)));
    }

    public boolean verify(@NonNull String login, @NonNull String password) {
        return passwordHash.verify(password.toCharArray(), find(login).getPassword());
    }
}
