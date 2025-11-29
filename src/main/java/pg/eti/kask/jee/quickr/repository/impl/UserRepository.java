package pg.eti.kask.jee.quickr.repository.impl;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(cb.equal(root.get("login"), login));
        return em.createQuery(query).getResultStream().findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(cb.equal(root.get("email"), email));
        return em.createQuery(query).getResultStream().findFirst();
    }

    @Override
    public boolean existsById(UUID id) {
        return em.find(User.class, id) != null;
    }

    @Override
    public boolean existsByLogin(String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        query.select(cb.count(root)).where(cb.equal(root.get("login"), login));
        Long count = em.createQuery(query).getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<User> root = query.from(User.class);
        query.select(cb.count(root)).where(cb.equal(root.get("email"), email));
        Long count = em.createQuery(query).getSingleResult();
        return count != null && count > 0;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        return em.createQuery(query).getResultList();
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
