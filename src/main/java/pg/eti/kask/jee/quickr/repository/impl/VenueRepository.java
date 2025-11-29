package pg.eti.kask.jee.quickr.repository.impl;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pg.eti.kask.jee.quickr.entity.Venue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class VenueRepository implements pg.eti.kask.jee.quickr.repository.api.VenueRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Venue> findById(UUID id) {
        return Optional.ofNullable(em.find(Venue.class, id));
    }

    @Override
    public List<Venue> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Venue> query = cb.createQuery(Venue.class);
        Root<Venue> root = query.from(Venue.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Venue entity) {
        em.persist(entity);
    }

    @Override
    public void update(Venue entity) {
        em.merge(entity);
    }

    @Override
    public void delete(Venue venue) {
        Venue venueToDelete = em.find(Venue.class, venue.getId());
        if (venueToDelete != null) {
            if (venueToDelete.getOrders() != null) {
                venueToDelete.getOrders().size();
            }
            em.remove(venueToDelete);
        }
    }

    @Override
    public List<Venue> findAllByUser(pg.eti.kask.jee.quickr.entity.User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Venue> query = cb.createQuery(Venue.class);
        Root<Venue> root = query.from(Venue.class);
        query.select(root).where(cb.equal(root.get("user"), user));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Venue> findAllByFilter(pg.eti.kask.jee.quickr.dto.VenueFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Venue> query = cb.createQuery(Venue.class);
        Root<Venue> root = query.from(Venue.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getName() != null && !filter.getName().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
        }

        if (filter.getVenueCategory() != null) {
            predicates.add(cb.equal(root.get("venueCategory"), filter.getVenueCategory()));
        }

        if (filter.getMinCapacity() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("capacity"), filter.getMinCapacity()));
        }

        if (filter.getMaxCapacity() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("capacity"), filter.getMaxCapacity()));
        }

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        return em.createQuery(query).getResultList();
    }

}
