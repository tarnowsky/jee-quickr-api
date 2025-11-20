package pg.eti.kask.jee.quickr.repository.impl;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pg.eti.kask.jee.quickr.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class UserRepository implements pg.eti.kask.jee.quickr.repository.api.UserRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return em.createQuery("select u from User u where u.login = :login", User.class)
                        .setParameter("login", login)
                        .getResultStream()
                        .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return em.createQuery("select u from User u where u.email = :email", User.class)
                        .setParameter("email", email)
                        .getResultStream()
                        .findFirst();
    }

    @Override
    public boolean existsById(UUID id) {
        return em.find(User.class, id) != null;
    }

    @Override
    public boolean existsByLogin(String login) {
        Long count =  em.createQuery("select count(u) from User u where u.login = :login", Long.class)
                .setParameter("login", login)
                .getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        Long count =  em.createQuery("select count(u) from User u where u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count != null && count > 0;
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
    public void create(User entity) {
        em.persist(entity);
    }

    @Override
    public void update(User entity) {
        em.merge(entity);
    }

    @Override
    public void delete(User user) {
        em.remove(em.find(User.class, user.getId()));
    }


}
