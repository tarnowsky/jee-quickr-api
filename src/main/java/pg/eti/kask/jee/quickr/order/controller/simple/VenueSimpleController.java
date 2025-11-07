package pg.eti.kask.jee.quickr.order.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pg.eti.kask.jee.quickr.component.DtoFunctionFactory;
import pg.eti.kask.jee.quickr.order.controller.api.VenueController;
import pg.eti.kask.jee.quickr.order.dto.GetOrdersResponse;
import pg.eti.kask.jee.quickr.order.dto.GetVenueResponse;
import pg.eti.kask.jee.quickr.order.dto.GetVenuesResponse;
import pg.eti.kask.jee.quickr.order.service.VenueService;

import java.util.UUID;

@RequestScoped
public class VenueSimpleController implements VenueController {

    private final VenueService service;
    private final DtoFunctionFactory factory;

    @Inject
    public VenueSimpleController(VenueService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetVenuesResponse getVenuesResponse() {
        return factory.returnVenuesFunction().apply(service.findAll());
    }

    @Override
    public GetVenueResponse getVenueResponse(UUID id) {
        return factory.returnVenueFunction().apply(service.find(id));
    }

    @Override
    public void deleteVenueRequest(UUID id) {
        service.delete(id);
    }

    @Override
    public GetOrdersResponse getOrdersByVenueId(UUID id) {
        return factory.returnOrdersFunction().apply(service.findAllOrdersByVenueId(id));
    }
}
