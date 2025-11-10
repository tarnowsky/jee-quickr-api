package pg.eti.kask.jee.quickr.component;

import jakarta.enterprise.context.ApplicationScoped;
import pg.eti.kask.jee.quickr.order.model.function.*;

@ApplicationScoped
public class ModelFunctionFactory {
    public OrderToModelFunction orderToModel() {
        return new OrderToModelFunction();
    }

    public ModelToOrderFunction modelToOrder() {
        return new ModelToOrderFunction();
    }

    public OrdersToModelFunction ordersToModel() {
        return new OrdersToModelFunction();
    }

    public OrderToEditModelFunction orderToEditModel() {
        return new OrderToEditModelFunction();
    }

    public UpdateOrderWithModelFunction updateOrderWithModel() {
        return new UpdateOrderWithModelFunction();
    }

    public VenueToModelFunction venueToModel() {
        return new VenueToModelFunction();
    }

    public VenuesToModelFunction venuesToModel() {
        return new VenuesToModelFunction();
    }
}
