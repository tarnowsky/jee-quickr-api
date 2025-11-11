package pg.eti.kask.jee.quickr.user.repository.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pg.eti.kask.jee.quickr.user.entity.User;
import pg.eti.kask.jee.quickr.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserPersistenceRepository implements UserRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Optional.ofNullable(
                em.createQuery("select u from User u where u.login = :login", User.class).getSingleResult()
        );
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public Optional<User> create(User entity) {
        em.persist(entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<User> update(User entity) {
        em.merge(entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<User> delete(UUID id) {
        User userToDelete = em.find(User.class, id);
        em.remove(userToDelete);
        return Optional.of(userToDelete);
    }


}
