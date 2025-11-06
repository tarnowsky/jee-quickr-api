package pg.eti.kask.jee.quickr.configuration.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pg.eti.kask.jee.quickr.component.DtoFunctionFactory;
import pg.eti.kask.jee.quickr.user.controller.simple.UserSimpleController;
import pg.eti.kask.jee.quickr.user.service.UserAvatarService;
import pg.eti.kask.jee.quickr.user.service.UserService;

@WebListener
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        String avatarDirectory = context.getInitParameter("avatar-directory");
        if (avatarDirectory == null) {
            avatarDirectory = System.getProperty("java.io.tmpdir") + "/avatars";
        }

        UserAvatarService avatarService = new UserAvatarService(avatarDirectory);
        UserService userService = (UserService) context.getAttribute("userService");

        event.getServletContext().setAttribute("userController", new UserSimpleController(
                userService,
                avatarService,
                new DtoFunctionFactory()
        ));
    }
}
