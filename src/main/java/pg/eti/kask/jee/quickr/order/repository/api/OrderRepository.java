package pg.eti.kask.jee.quickr.order.repository.api;

import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.repository.api.Repository;

import java.util.UUID;

public interface OrderRepository extends Repository<Order, UUID> {
}
