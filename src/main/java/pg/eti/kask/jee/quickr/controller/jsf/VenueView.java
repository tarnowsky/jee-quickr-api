package pg.eti.kask.jee.quickr.controller.jsf;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pg.eti.kask.jee.quickr.component.ModelFunctionFactory;
import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.entity.Venue;
import pg.eti.kask.jee.quickr.entity.enums.UserRoles;
import pg.eti.kask.jee.quickr.model.order.OrderModel;
import pg.eti.kask.jee.quickr.model.venue.VenueModel;
import pg.eti.kask.jee.quickr.service.OrderService;
import pg.eti.kask.jee.quickr.service.VenueService;
import jakarta.security.enterprise.SecurityContext;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ViewScoped
@Named
public class VenueView implements Serializable {

    private VenueService venueService;
    private OrderService orderService;

    private SecurityContext securityContext;
    private ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private VenueModel venue;

    @EJB
    public void setVenueService(VenueService venueService) {
        this.venueService = venueService;
    }

    @EJB
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Inject
    public VenueView(ModelFunctionFactory factory, SecurityContext securityContext) {
        this.factory = factory;
        this.securityContext = securityContext;
    }

    public void init() throws IOException {
        java.util.Optional<Venue> venueOptional = venueService.findById(id);
        if (venueOptional.isPresent()) {
            this.venue = factory.venueToModel().apply(venueOptional.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(
                    HttpServletResponse.SC_NOT_FOUND, "Venue not found");
        }
    }

    public String deleteAction(OrderModel order) {
        Venue venueEntity = venueService.findById(venue.getId()).get();
        Order orderEntity = orderService.findById(UUID.fromString(order.getId())).get();

        // Utwórz modyfikowalną kopię listy
        List<Order> mutableOrders = new ArrayList<>(venueEntity.getOrders());
        mutableOrders.remove(orderEntity);
        venueEntity.setOrders(mutableOrders);

        // Usuń zamówienie z venue
        venueService.update(venueEntity);

        // Usuń zamówienie z db
        orderService.delete(orderEntity.getId());
        return "venue_view?faces-redirect=true&includeViewParams=true";
    }

    public String redirectToUserVenueOrders() {
        if (!securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return "user_venue_orders?faces-redirect=true&venueId=" + id;
        }
        return "";
    } 
}
