package pg.eti.kask.jee.quickr.order.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pg.eti.kask.jee.quickr.component.ModelFunctionFactory;
import pg.eti.kask.jee.quickr.order.model.VenuesModel;
import pg.eti.kask.jee.quickr.order.service.OrderService;
import pg.eti.kask.jee.quickr.order.service.VenueService;

@RequestScoped
@Named
public class VenueList {
    private final VenueService venueService;
    private VenuesModel venues;
    private final ModelFunctionFactory factory;
    private final OrderService orderService;

    @Inject
    public VenueList(VenueService service, ModelFunctionFactory factory, OrderService orderService) {
        this.venueService = service;
        this.factory = factory;
        this.orderService = orderService;
    }

    public VenuesModel getVenues() {
        if (venues == null) {
            venues = factory.venuesToModel().apply(venueService.findAll());
        }
        return venues;
    }

    public String deleteAction(VenuesModel.Venue venue) {
        venueService.find(venue.getId()).getOrders()
                .forEach(order -> orderService.delete(order.getId()));
        venueService.delete(venue.getId());
        return "venue_list?faces-redirect=true";
    }
}
