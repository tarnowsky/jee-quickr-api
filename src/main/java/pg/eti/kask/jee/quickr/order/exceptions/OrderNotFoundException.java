package pg.eti.kask.jee.quickr.order.exceptions;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.NotFoundException;

@ApplicationException
public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
