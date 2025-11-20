package pg.eti.kask.jee.quickr.controller.impl;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import pg.eti.kask.jee.quickr.component.DtoFunctionFactory;
import pg.eti.kask.jee.quickr.dto.venue.GetVenueResponse;
import pg.eti.kask.jee.quickr.dto.venue.GetVenuesResponse;
import pg.eti.kask.jee.quickr.dto.venue.PutVenueRequest;
import pg.eti.kask.jee.quickr.entity.enums.UserRoles;
import pg.eti.kask.jee.quickr.service.VenueService;

import java.util.UUID;
import java.util.logging.Level;

@Log
@Path("")
public class VenueController implements pg.eti.kask.jee.quickr.controller.api.VenueController {

    private final VenueService venueService;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }


    @Inject
    public VenueController(
            VenueService venueService,
            DtoFunctionFactory factory,
            @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo
    ) {
        this.venueService = venueService;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    @RolesAllowed(UserRoles.USER)
    public GetVenuesResponse getVenues() {
        return factory.returnVenuesFunction().apply(venueService.findAll());
    }

    @Override
    public GetVenueResponse getVenue(UUID id) {
        return venueService.findById(id).map(factory.returnVenueFunction()).orElseThrow(NotFoundException::new);
    }

    @Override
    @RolesAllowed(UserRoles.ADMIN)
    public void putVenue(UUID id, PutVenueRequest putVenueRequest) {
        try {
            venueService.create(
                    factory.createVenueFunction().apply(id, putVenueRequest));
            String location = uriInfo.getBaseUriBuilder()
                    .path("api")
                    .path("weaponfamilies")
                    .path(id.toString())
                    .build()
                    .toString();
            response.setHeader("Location", location);
            throw new WebApplicationException(Response.status(Response.Status.CREATED).build());
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }

    @Override
    public void deleteVenue(UUID id) {
        venueService.findById(id).ifPresentOrElse(
                v -> venueService.delete(id), NotFoundException::new
        );
    }
}
