package pg.eti.kask.jee.quickr.repository.api;

import pg.eti.kask.jee.quickr.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {
    Optional<User> findByLogin(String login);
    Optional<User> findByEmail(String email);
    boolean existsById(UUID id);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
}
