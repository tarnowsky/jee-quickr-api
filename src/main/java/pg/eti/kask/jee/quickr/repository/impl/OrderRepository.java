package pg.eti.kask.jee.quickr.repository.impl;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.entity.Venue;
import pg.eti.kask.jee.quickr.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class OrderRepository implements pg.eti.kask.jee.quickr.repository.api.OrderRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return Optional.ofNullable(em.find(Order.class, id));
    }

    @Override
    public List<Order> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Order entity) {
        em.persist(entity);
    }

    @Override
    public void update(Order entity) {
        em.merge(entity);
    }

    @Override
    public void delete(Order order) {
        em.remove(em.find(Order.class, order.getId()));
    }

    @Override
    public List<Order> findAllByUser(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root).where(cb.equal(root.get("user"), user));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Order> findAllByVenue(Venue venue) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root).where(cb.equal(root.get("venue"), venue));
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<Order> findByIdAndUser(UUID id, User user) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Order> query = cb.createQuery(Order.class);
            Root<Order> root = query.from(Order.class);
            query.select(root).where(cb.and(
                    cb.equal(root.get("id"), id),
                    cb.equal(root.get("user"), user)));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
