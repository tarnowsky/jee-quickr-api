package pg.eti.kask.jee.quickr.exception;

import jakarta.ejb.ApplicationException;

/**
 * Exception thrown when an optimistic locking conflict occurs during an Order
 * update.
 * Marked with @ApplicationException(rollback = true) to ensure transaction
 * rollback
 * without triggering system-level EJB logs (CNTR0020E).
 */
@ApplicationException(rollback = true)
public class OrderOptimisticLockException extends Exception {
    public OrderOptimisticLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
