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

    @Override
    public List<Order> findAllByFilter(pg.eti.kask.jee.quickr.dto.OrderFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);

        java.util.List<jakarta.persistence.criteria.Predicate> predicates = new java.util.ArrayList<>();

        if (filter.getName() != null && !filter.getName().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
        }

        if (filter.getMinPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
        }

        if (filter.getMaxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
        }

        if (filter.getMinItemCount() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("itemCount"), filter.getMinItemCount()));
        }

        if (filter.getMaxItemCount() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("itemCount"), filter.getMaxItemCount()));
        }

        if (filter.getVenueId() != null) {
            predicates.add(cb.equal(root.get("venue").get("id"), filter.getVenueId()));
        }

        query.select(root).where(cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0])));

        return em.createQuery(query).getResultList();
    }
}
