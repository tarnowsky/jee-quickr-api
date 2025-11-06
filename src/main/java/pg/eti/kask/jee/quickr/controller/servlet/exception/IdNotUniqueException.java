package pg.eti.kask.jee.quickr.controller.servlet.exception;

/**
 * Exception indicates that resource with given ID already exists and 409 status code should be returned.
 */
public class IdNotUniqueException extends HttpRequestException {

    /**
     * HTTP conflict response code.
     */
    private static final int RESPONSE_CODE = 409;

    public IdNotUniqueException() {
        super(RESPONSE_CODE);
    }

    /**
     * @param message the detail message
     */
    public IdNotUniqueException(String message) {
        super(message, RESPONSE_CODE);
    }

    /**
     * @param message the detail message
     * @param cause   the cause
     */
    public IdNotUniqueException(String message, Throwable cause) {
        super(message, cause, RESPONSE_CODE);
    }

    /**
     * @param cause the cause
     */
    public IdNotUniqueException(Throwable cause) {
        super(cause, RESPONSE_CODE);
    }

    /**
     * @param message            the detail message
     * @param cause              the cause
     * @param enableSuppression  whether suppression is enabled or disabled
     * @param writableStackTrace whether the stack trace should be writable
     */
    public IdNotUniqueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, RESPONSE_CODE);
    }

}
