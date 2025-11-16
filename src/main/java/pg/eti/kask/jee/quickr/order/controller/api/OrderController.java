package pg.eti.kask.jee.quickr.order.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pg.eti.kask.jee.quickr.order.dto.GetOrderResponse;
import pg.eti.kask.jee.quickr.order.dto.GetOrdersResponse;
import pg.eti.kask.jee.quickr.order.dto.PatchOrderRequest;
import pg.eti.kask.jee.quickr.order.dto.PutOrderRequest;

import java.util.UUID;

@Path("")
public interface OrderController {

    @GET
    @Path("/orders")
    @Produces(MediaType.APPLICATION_JSON)
    GetOrdersResponse getOrders();

    @GET
    @Path("/orders/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetOrderResponse getOrder(@PathParam("id") UUID id);

    @GET
    @Path("/venues/{id}/orders")
    @Produces(MediaType.APPLICATION_JSON)
    GetOrdersResponse getVenueOrders(@PathParam("id") UUID id);

    @GET
    @Path("/users/{id}/orders")
    @Produces(MediaType.APPLICATION_JSON)
    GetOrdersResponse getUserOrders(@PathParam("id") UUID id);

    @PUT
    @Path("/venues/{venueId}/orders/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    void putOrder(@PathParam("venueId") UUID venueId, @PathParam("orderId") UUID orderId, PutOrderRequest req);

    @PATCH
    @Path("/venues/{venueId}/orders/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    void patchOrder(@PathParam("venueId") UUID venueId, @PathParam("orderId") UUID orderId, PatchOrderRequest req);

    @DELETE
    @Path("/orders/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void deleteOrder(@PathParam("id") UUID id);
}
