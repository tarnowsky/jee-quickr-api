package pg.eti.kask.jee.quickr.datastore.component;

import lombok.NonNull;
import lombok.extern.java.Log;
import pg.eti.kask.jee.quickr.serialization.component.CloningUtility;
import pg.eti.kask.jee.quickr.user.entity.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Log
public class DataStore {
    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    private final CloningUtility cloningUtility;

    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<User> findAllUsers(){
        return users.values().stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized Optional<User> findUserById(UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    public synchronized Optional<User> findUserByLogin(String login) {
        return users.values().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
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
}
