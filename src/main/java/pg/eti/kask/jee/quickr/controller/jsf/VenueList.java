package pg.eti.kask.jee.quickr.controller.jsf;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pg.eti.kask.jee.quickr.component.ModelFunctionFactory;
import pg.eti.kask.jee.quickr.model.venue.VenuesModel;
import pg.eti.kask.jee.quickr.service.OrderService;
import pg.eti.kask.jee.quickr.service.VenueService;

@ViewScoped
@Named
public class VenueList implements java.io.Serializable {
    private VenuesModel venues;
    private VenueService venueService;
    private OrderService orderService;

    private final ModelFunctionFactory factory;

    @EJB
    public void setVenueService(VenueService venueService) {
        this.venueService = venueService;
    }

    @EJB
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Inject
    public VenueList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    public VenuesModel getVenues() {
        if (venues == null) {
            venues = factory.venuesToModel().apply(venueService.findAll());
        }
        return venues;
    }

    public void deleteAction(VenuesModel.Venue venue) {
        venueService.findById(venue.getId()).ifPresent(v -> {
            v.getOrders().forEach(order -> orderService.delete(order.getId()));
            venueService.delete(v.getId());
        });
        venues = null;
    }
}
