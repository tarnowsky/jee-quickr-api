package pg.eti.kask.jee.quickr.order.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pg.eti.kask.jee.quickr.order.dto.GetOrdersResponse;
import pg.eti.kask.jee.quickr.order.dto.GetVenueResponse;
import pg.eti.kask.jee.quickr.order.dto.GetVenuesResponse;

import java.util.UUID;

@Path("")
public interface VenueController {

    @GET
    @Path("/venues")
    @Produces(MediaType.APPLICATION_JSON)
    GetVenuesResponse getVenues();

    @GET
    @Path("/venues/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetVenueResponse getVenue(@PathParam("id") UUID id);


    @DELETE
    @Path("/venues/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void deleteVenue(@PathParam("id") UUID id);
}
