package pg.eti.kask.jee.quickr.component;

import jakarta.enterprise.context.ApplicationScoped;
import pg.eti.kask.jee.quickr.dto.order.function.*;
import pg.eti.kask.jee.quickr.dto.user.function.*;
import pg.eti.kask.jee.quickr.dto.venue.function.CreateVenueFunction;
import pg.eti.kask.jee.quickr.dto.venue.function.ReturnVenueFunction;
import pg.eti.kask.jee.quickr.dto.venue.function.ReturnVenuesFunction;

@ApplicationScoped
public class DtoFunctionFactory {
    public CreateUserFunction createUserFunction() {
        return new CreateUserFunction();
    }
    public ReturnUserFunction returnUserFunction() {
        return new ReturnUserFunction();
    }
    public ReturnUsersFunction returnUsersFunction() {
        return new ReturnUsersFunction();
    }
    public UpdateUserFunction updateUserFunction() {
        return new UpdateUserFunction();
    }

    public CreateOrderFunction createOrderFunction() {
        return new CreateOrderFunction();
    }

    public ReturnOrderFunction returnOrderFunction() {
        return new ReturnOrderFunction();
    }

    public ReturnOrdersFunction returnOrdersFunction() {
        return new ReturnOrdersFunction();
    }

    public ReturnVenueFunction returnVenueFunction() {
        return new ReturnVenueFunction();
    }

    public ReturnVenuesFunction returnVenuesFunction() {
        return new ReturnVenuesFunction();
    }

    public UpdateOrderFunction updateOrderFunction() {
        return new UpdateOrderFunction();
    }

    public UpdateUserPasswordFunction updateUserPasswordFunction() { return new UpdateUserPasswordFunction(); }

    public CreateVenueFunction createVenueFunction() {
        return new CreateVenueFunction();
    }

    public CreateOrderWithVenueFunction createOrderWithVenueFunction() {
        return new CreateOrderWithVenueFunction();
    }
}
