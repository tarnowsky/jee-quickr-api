package pg.eti.kask.jee.quickr.order.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pg.eti.kask.jee.quickr.component.ModelFunctionFactory;
import pg.eti.kask.jee.quickr.controller.servlet.exception.NotFoundException;
import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.entity.Venue;
import pg.eti.kask.jee.quickr.order.model.OrderModel;
import pg.eti.kask.jee.quickr.order.model.VenueModel;
import pg.eti.kask.jee.quickr.order.service.OrderService;
import pg.eti.kask.jee.quickr.order.service.VenueService;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ViewScoped
@Named
public class VenueView implements Serializable {
    private final VenueService venueService;
    private final ModelFunctionFactory factory;
    private final OrderService orderService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private VenueModel venue;

    @Inject
    public VenueView(VenueService venueService, ModelFunctionFactory factory, OrderService orderService) {
        this.venueService = venueService;
        this.factory = factory;
        this.orderService = orderService;
    }

    public void init() throws IOException {
        try {
            Venue venue = venueService.find(id);
            this.venue = factory.venueToModel().apply(venue);
        } catch (NotFoundException ex) {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(
                    HttpServletResponse.SC_NOT_FOUND, "Venue not found"
            );
        }
    }

    public String deleteAction(OrderModel order) {
        Venue venueEntity = venueService.find(venue.getId());
        Order orderEntity = orderService.find(UUID.fromString(order.getId()));

        // Utwórz modyfikowalną kopię listy
        List<Order> mutableOrders = new ArrayList<>(venueEntity.getOrders());
        mutableOrders.remove(orderEntity);
        venueEntity.setOrders(mutableOrders);

        // Usuń zamówienie z serwisu
        orderService.delete(orderEntity.getId());
        return "venue_view?faces-redirect=true&amp;id=" + id;
    }
}
