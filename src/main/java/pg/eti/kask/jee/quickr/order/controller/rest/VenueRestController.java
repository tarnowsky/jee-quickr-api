package pg.eti.kask.jee.quickr.order.controller.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import pg.eti.kask.jee.quickr.component.DtoFunctionFactory;
import pg.eti.kask.jee.quickr.order.controller.api.VenueController;
import pg.eti.kask.jee.quickr.order.dto.GetVenueResponse;
import pg.eti.kask.jee.quickr.order.dto.GetVenuesResponse;
import pg.eti.kask.jee.quickr.order.service.VenueService;

import java.util.UUID;

@Path("")
public class VenueRestController implements VenueController {

    private final VenueService venueService;
    private final DtoFunctionFactory factory;

    @Inject
    public VenueRestController(VenueService venueService, DtoFunctionFactory factory) {
        this.venueService = venueService;
        this.factory = factory;
    }

    @Override
    public GetVenuesResponse getVenues() {
        return factory.returnVenuesFunction().apply(venueService.findAll());
    }

    @Override
    public GetVenueResponse getVenue(UUID id) {
        return factory.returnVenueFunction().apply(venueService.find(id));
    }

    @Override
    public void deleteVenue(UUID id) {
        venueService.delete(id);
    }
}
