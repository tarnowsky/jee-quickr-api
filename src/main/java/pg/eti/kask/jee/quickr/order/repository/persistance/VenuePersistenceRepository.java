package pg.eti.kask.jee.quickr.order.repository.persistance;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pg.eti.kask.jee.quickr.order.entity.Venue;
import pg.eti.kask.jee.quickr.order.repository.api.VenueRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class VenuePersistenceRepository implements VenueRepository {

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
    public Optional<Venue> create(Venue entity) {
        em.persist(entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<Venue> update(Venue entity) {
        em.merge(entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<Venue> delete(UUID id) {
        Venue venueToDelete = em.find(Venue.class, id);
        em.remove(venueToDelete);
        return Optional.of(venueToDelete);
    }


}
