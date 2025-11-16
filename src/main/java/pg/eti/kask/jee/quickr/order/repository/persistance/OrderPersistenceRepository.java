package pg.eti.kask.jee.quickr.order.repository.persistance;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.BadRequestException;
import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.repository.api.OrderRepository;

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
    public Optional<Order> create(Order entity) {
        em.persist(entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<Order> update(Order entity) {
        em.merge(entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<Order> delete(UUID id) {
        Order entityToDelete = em.find(Order.class, id);
        em.remove(entityToDelete);
        return Optional.of(entityToDelete);
    }
}
