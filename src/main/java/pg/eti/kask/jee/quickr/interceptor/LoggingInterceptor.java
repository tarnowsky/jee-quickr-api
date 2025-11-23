package pg.eti.kask.jee.quickr.interceptor;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Arrays;
import java.util.logging.Logger;

@Interceptor
@Loggable
public class LoggingInterceptor implements Serializable {

    @Inject
    private HttpServletRequest request;

    private final Logger logger = Logger.getLogger(LoggingInterceptor.class.getName());

    @AroundInvoke
    public Object logMethodCall(InvocationContext context) throws Exception {
        String userName = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "anonymous";
        String methodName = context.getMethod().getName();
        Object[] parameters = context.getParameters();

        logger.warning("\b\b\b\b\b\b\b\b\b\b\b[DEBUG] User '" + userName + "' called method '" + methodName + "' with parameters: " + Arrays.toString(parameters));

        try {
            Object result = context.proceed();
            logger.warning("\b\b\b\b\b\b\b\b\b\b\b[DEBUG] Method '" + methodName + "' executed successfully.");
            return result;
        } catch (Exception e) {
            logger.warning("Method '" + methodName + "' threw an exception: " + e.getMessage());
            throw e;
        }
    }
}

