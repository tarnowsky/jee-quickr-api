package pg.eti.kask.jee.quickr.user.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pg.eti.kask.jee.quickr.datastore.component.DataStore;
import pg.eti.kask.jee.quickr.user.entity.User;
import pg.eti.kask.jee.quickr.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class UserInMemoryRepository implements UserRepository {

    private final DataStore dataStore;

    @Inject
    public UserInMemoryRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return dataStore.findUserByLogin(login);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return dataStore.findUserById(id);
    }

    @Override
    public List<User> findAll() {
        return dataStore.findAllUsers();
    }

    @Override
    public Optional<User> create(User user) {
        return dataStore.createUser(user);
    }

    @Override
    public Optional<User> update(User user) {
        return dataStore.updateUser(user);
    }

    @Override
    public Optional<User> delete(UUID id) {
        return dataStore.removeUserById(id);
    }
}
