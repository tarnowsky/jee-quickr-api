package pg.eti.kask.jee.quickr.user.exceptions;

public class UserIdNotUniqueException extends RuntimeException {
    public UserIdNotUniqueException(String message) {
        super(message);
    }
}
