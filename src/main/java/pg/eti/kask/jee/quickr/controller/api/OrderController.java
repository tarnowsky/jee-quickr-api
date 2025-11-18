package pg.eti.kask.jee.quickr.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pg.eti.kask.jee.quickr.dto.order.*;

import java.util.UUID;

@Path("")
public interface OrderController {

    @GET
    @Path("/orders")
    @Produces(MediaType.APPLICATION_JSON)
    GetOrdersResponse getOrders();

    @GET
    @Path("/orders/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    GetOrderResponse getOrder(@PathParam("orderId") UUID id);

    @GET
    @Path("/venues/{venueId}/orders")
    @Produces(MediaType.APPLICATION_JSON)
    GetOrdersResponse getVenueOrders(@PathParam("venueId") UUID id);

    @GET
    @Path("/users/{id}/orders")
    @Produces(MediaType.APPLICATION_JSON)
    GetOrdersResponse getUserOrders(@PathParam("id") UUID id);

    @PUT
    @Path("/venues/orders/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    void putOrder(@PathParam("orderId") UUID orderId, PutOrderRequest req);

    @PUT
    @Path("/venues/{venueId}/orders/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    void putOrderWithVenueId(@PathParam("venueId") UUID venueId, @PathParam("orderId") UUID orderId, PutOrderWithVenueRequest req);

    @PATCH
    @Path("/orders/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    void patchOrder(@PathParam("orderId") UUID orderId, PatchOrderRequest req);

    @DELETE
    @Path("/orders/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    void deleteOrder(@PathParam("orderId") UUID orderId);
}
