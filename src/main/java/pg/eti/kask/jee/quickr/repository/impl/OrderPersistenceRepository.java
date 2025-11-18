package pg.eti.kask.jee.quickr.repository.impl;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.entity.Venue;
import pg.eti.kask.jee.quickr.repository.api.OrderRepository;
import pg.eti.kask.jee.quickr.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class OrderPersistenceRepository implements OrderRepository {

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
        return em.createQuery("select o from Order o", Order.class).getResultList();
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
        return em.createQuery("select o from Order o where o.user = :user", Order.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Order> findAllByVenue(Venue venue) {
        return em.createQuery("select o from Order o where o.venue = :venue", Order.class)
                .setParameter("venue", venue)
                .getResultList();
    }

    @Override
    public Optional<Order> findByIdAndUser(UUID id, User user) {
        try {
            return Optional.of(em.createQuery("select o from Order o where o.id = :id and o.user = :user", Order.class)
                    .setParameter("user", user)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
