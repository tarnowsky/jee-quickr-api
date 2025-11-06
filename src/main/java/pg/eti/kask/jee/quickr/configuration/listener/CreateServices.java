package pg.eti.kask.jee.quickr.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pg.eti.kask.jee.quickr.crypto.component.Pbkdf2PasswordHash;
import pg.eti.kask.jee.quickr.datastore.component.DataStore;
import pg.eti.kask.jee.quickr.user.repository.api.UserRepository;
import pg.eti.kask.jee.quickr.user.repository.memory.UserInMemoryRepository;
import pg.eti.kask.jee.quickr.user.service.UserService;

@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");

        UserRepository userRepository = new UserInMemoryRepository(dataSource);

        event.getServletContext().setAttribute("userService", new UserService(userRepository, new Pbkdf2PasswordHash()));
    }
}
