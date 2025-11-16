package pg.eti.kask.jee.quickr.order.controller.rest;

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
import pg.eti.kask.jee.quickr.order.controller.api.OrderController;
import pg.eti.kask.jee.quickr.order.dto.GetOrderResponse;
import pg.eti.kask.jee.quickr.order.dto.GetOrdersResponse;
import pg.eti.kask.jee.quickr.order.dto.PatchOrderRequest;
import pg.eti.kask.jee.quickr.order.dto.PutOrderRequest;
import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.entity.Venue;
import pg.eti.kask.jee.quickr.order.service.OrderService;
import pg.eti.kask.jee.quickr.order.service.VenueService;
import pg.eti.kask.jee.quickr.user.service.UserService;

import java.util.UUID;
import java.util.logging.Level;

@Log
@Path("")
public class OrderRestController implements OrderController {
    private final DtoFunctionFactory factory;
    private final OrderService orderService;
    private final VenueService venueService;
    private final UserService userService;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Inject
    public OrderRestController(DtoFunctionFactory factory, OrderService orderService, VenueService venueService, UserService userService,
                               @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.orderService = orderService;
        this.venueService = venueService;
        this.userService = userService;
        this.uriInfo = uriInfo;
    }

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public GetOrdersResponse getOrders() {
        return factory.returnOrdersFunction().apply(orderService.findAll());
    }

    @Override
    public GetOrderResponse getOrder(UUID venueId, UUID orderId) {
        try {
            venueService.find(venueId);
            return factory.returnOrderFunction().apply(orderService.find(orderId));
        } catch (NotFoundException ex) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Override
    public GetOrdersResponse getVenueOrders(UUID id) {
        return factory.returnOrdersFunction().apply(venueService.find(id).getOrders());
    }

    @Override
    public GetOrdersResponse getUserOrders(UUID id) {
        return factory.returnOrdersFunction().apply(userService.findAllOrdersByUserId(id));
    }

    @Override
    public void putOrder(UUID venueId, UUID orderId, PutOrderRequest req) {

        try {
            Order newOrder = orderService.create(factory.createOrderFunction().apply(orderId, req));
            Venue venueToUpdate = venueService.find(venueId);

            newOrder.setVenue(venueToUpdate);
            orderService.update(newOrder);

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(OrderController.class, "getOrder")
                    .build(orderId)
                    .toString());

            throw new WebApplicationException(Response.Status.CREATED);

        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }

    }

    @Override
    public void patchOrder(UUID venueId, UUID orderId, PatchOrderRequest req) {
        try {
            venueService.find(venueId);
            orderService.update(factory.updateOrderFunction().apply(orderService.find(orderId), req));

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(OrderController.class, "getOrder")
                    .build(orderId)
                    .toString());

            throw new WebApplicationException(Response.Status.NO_CONTENT);
        } catch (NotFoundException ex) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Override
    public void deleteOrder(UUID id) {
        orderService.delete(id);
    }
}
