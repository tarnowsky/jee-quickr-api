package pg.eti.kask.jee.quickr.controller.jsf;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pg.eti.kask.jee.quickr.component.ModelFunctionFactory;
import pg.eti.kask.jee.quickr.model.venue.VenuesModel;
import pg.eti.kask.jee.quickr.service.OrderService;
import pg.eti.kask.jee.quickr.service.VenueService;

@RequestScoped
@Named
public class VenueList {
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

    public String deleteAction(VenuesModel.Venue venue) {
        venueService.findById(venue.getId()).get().getOrders()
                .forEach(order -> orderService.delete(order.getId()));
        venueService.delete(venue.getId());
        return "venue_list?faces-redirect=true";
    }
}
