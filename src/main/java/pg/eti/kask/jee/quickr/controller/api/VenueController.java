package pg.eti.kask.jee.quickr.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pg.eti.kask.jee.quickr.dto.venue.GetVenueResponse;
import pg.eti.kask.jee.quickr.dto.venue.GetVenuesResponse;
import pg.eti.kask.jee.quickr.dto.venue.PutVenueRequest;

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

    @PUT
    @Path("/venues/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void putVenue(@PathParam("id") UUID id, PutVenueRequest putVenueRequest);

    @DELETE
    @Path("/venues/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void deleteVenue(@PathParam("id") UUID id);
}
