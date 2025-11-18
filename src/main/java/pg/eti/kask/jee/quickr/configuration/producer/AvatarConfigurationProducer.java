package pg.eti.kask.jee.quickr.configuration.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;

@ApplicationScoped
public class AvatarConfigurationProducer {
    @Inject
    private ServletContext servletContext;

    @Produces
    @Named("avatarDirectory")
    public String produceAvatarBasePath() {
        return servletContext.getInitParameter("avatar-directory");
    }
}
