package pg.eti.kask.jee.quickr.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pg.eti.kask.jee.quickr.datastore.component.DataStore;
import pg.eti.kask.jee.quickr.serialization.component.CloningUtility;

@WebListener
public class CreateDataSource implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("datasource", new DataStore(new CloningUtility()));
    }
}
