package pg.eti.kask.jee.quickr.order.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pg.eti.kask.jee.quickr.datastore.component.DataStore;
import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.repository.api.OrderRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class OrderInMemoryRepository implements OrderRepository {

    private final DataStore dataStore;

    @Inject
    public OrderInMemoryRepository(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Optional<Order> findById(UUID id){
        return dataStore.findOrderById(id);
    }

    @Override
    public List<Order> findAll(){
        return dataStore.findAllOrders();
    }

    @Override
    public Optional<Order> create(Order entity){
        return dataStore.createOrder(entity);
    }

    @Override
    public Optional<Order> update(Order entity){
        return dataStore.updateOrder(entity);
    }

    @Override
    public Optional<Order> delete(UUID id){
        return dataStore.removeOrderById(id);
    }

}
