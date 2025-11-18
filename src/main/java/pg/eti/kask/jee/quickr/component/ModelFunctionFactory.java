package pg.eti.kask.jee.quickr.component;

import jakarta.enterprise.context.ApplicationScoped;
import pg.eti.kask.jee.quickr.model.order.function.*;
import pg.eti.kask.jee.quickr.model.venue.function.VenueToModelFunction;
import pg.eti.kask.jee.quickr.model.venue.function.VenuesToModelFunction;
import pg.eti.kask.jee.quickr.service.UserService;

@ApplicationScoped
public class ModelFunctionFactory {
    public OrderToModelFunction orderToModel() {
        return new OrderToModelFunction();
    }

    public ModelToOrderFunction modelToOrder(UserService userService) {
        return new ModelToOrderFunction(userService);
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
