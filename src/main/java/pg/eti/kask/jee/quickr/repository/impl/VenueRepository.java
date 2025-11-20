package pg.eti.kask.jee.quickr.repository.impl;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pg.eti.kask.jee.quickr.entity.Venue;

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
        return em.createQuery("select v from Venue v", Venue.class).getResultList();
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
            em.remove(venue);
        }
    }


}
