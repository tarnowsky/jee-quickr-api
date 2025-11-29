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

    @Getter
    private pg.eti.kask.jee.quickr.dto.OrderFilter filter = new pg.eti.kask.jee.quickr.dto.OrderFilter();

    public void init() {
        // Set venue ID in filter
        filter.setVenueId(venueId);

        // Use the filter service directly
        List<Order> filteredOrders = orderService.findAllByFilter(filter);

        // Filter by user if not admin (service handles roles, but we need to ensure own
        // orders for user)
        // Actually, findAllByFilter doesn't filter by user automatically unless we add
        // it to criteria or check here.
        // Let's rely on service check or filter manually here for safety if service
        // returns all.
        // Wait, OrderService.findAllByFilter is @RolesAllowed({ADMIN, USER}).
        // But it uses OrderRepository.findAllByFilter which just runs criteria.
        // We need to ensure regular users only see their own orders.

        if (!securityContext.isCallerInRole(UserRoles.ADMIN)) {
            String login = securityContext.getCallerPrincipal().getName();
            filteredOrders = filteredOrders.stream()
                    .filter(o -> o.getUser().getLogin().equals(login))
                    .collect(Collectors.toList());
        }

        this.orders = factory.ordersToModel().apply(filteredOrders);
    }

    public void filterAction() {
        init();
    }

    public String deleteAction(OrdersModel.Order order) {
        orderService.delete(order.getId());
        return "user_venue_orders?faces-redirect=true&includeViewParams=true";
    }

    public String redirectToVenueView() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return "venue_view?faces-redirect=true&id=" + venueId;
        }
        return null;
    }
}
