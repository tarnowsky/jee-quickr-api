package pg.eti.kask.jee.quickr.order.controller.api;

import pg.eti.kask.jee.quickr.order.dto.GetVenueResponse;
import pg.eti.kask.jee.quickr.order.dto.GetVenuesResponse;

import java.util.UUID;

public interface VenueController {
    GetVenuesResponse getVenuesResponse();
    GetVenueResponse getVenueResponse(UUID id);
    void deleteVenueRequest(UUID id);

}
