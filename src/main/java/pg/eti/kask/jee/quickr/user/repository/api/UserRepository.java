package pg.eti.kask.jee.quickr.user.repository.api;

import pg.eti.kask.jee.quickr.repository.api.Repository;
import pg.eti.kask.jee.quickr.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {
    Optional<User> findByLogin(String login);
}
