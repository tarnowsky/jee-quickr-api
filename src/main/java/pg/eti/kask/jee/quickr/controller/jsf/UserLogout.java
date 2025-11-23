package pg.eti.kask.jee.quickr.controller.jsf;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;

@RequestScoped
@Named
public class UserLogout {

    private final HttpServletRequest request;

    @Inject
    public UserLogout(HttpServletRequest request) {
        this.request = request;
    }

    @SneakyThrows
    public String logoutAction() {
        request.logout();
        return "/venue/venue_list.xhtml?faces-redirect=true";
    }
}
