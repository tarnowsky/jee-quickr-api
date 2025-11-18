package pg.eti.kask.jee.quickr.controller.exception;

import jakarta.ejb.ApplicationException;
import jakarta.ws.rs.NotFoundException;

@ApplicationException
public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
