package pg.eti.kask.jee.quickr.controller.exception;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.NotFoundException;

@ApplicationException
public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
