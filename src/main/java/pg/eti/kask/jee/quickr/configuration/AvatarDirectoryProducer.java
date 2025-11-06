package pg.eti.kask.jee.quickr.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;

@ApplicationScoped
public class AvatarDirectoryProducer {

    @Produces
    @Named("avatarDirectory")
    public String produceAvatarDirectory(ServletContext context) {
        String directory = context.getInitParameter("avatar-directory");
        if (directory == null) {
            throw new IllegalStateException("avatar-directory not configured in web.xml");
        }
        return directory;
    }
}
