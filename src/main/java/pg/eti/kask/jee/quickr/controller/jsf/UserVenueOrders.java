package pg.eti.kask.jee.quickr.controller.jsf;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import pg.eti.kask.jee.quickr.component.ModelFunctionFactory;
import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.entity.enums.UserRoles;
import pg.eti.kask.jee.quickr.model.order.OrdersModel;
import pg.eti.kask.jee.quickr.service.OrderService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class UserVenueOrders implements Serializable {

    private OrderService orderService;
    private final ModelFunctionFactory factory;
    private final SecurityContext securityContext;

    @Setter
    @Getter
    private UUID venueId;

    @Getter
    private OrdersModel orders;

    @EJB
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Inject
    public UserVenueOrders(ModelFunctionFactory factory, SecurityContext securityContext) {
        this.factory = factory;
        this.securityContext = securityContext;
    }

    public void init() {
        // Get all orders for the current user at this specific venue
        List<Order> userOrders = orderService.findAllForCallerPrincipal();

        // Filter to only orders for this venue
        List<Order> venueUserOrders = userOrders.stream()
                .filter(order -> order.getVenue() != null && order.getVenue().getId().equals(venueId))
                .collect(Collectors.toList());

        this.orders = factory.ordersToModel().apply(venueUserOrders);
    }

    public String deleteAction(OrdersModel.Order order) {
        orderService.delete(order.getId());
        return "user_venue_orders?faces-redirect=true&includeViewParams=true";
    }

    public String redirectToVenueView() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return "venue_view?faces-redirect=true&id=" + venueId;
        }
        return "";
    }
}
