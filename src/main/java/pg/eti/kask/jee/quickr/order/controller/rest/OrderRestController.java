package pg.eti.kask.jee.quickr.order.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pg.eti.kask.jee.quickr.component.DtoFunctionFactory;
import pg.eti.kask.jee.quickr.order.controller.api.OrderController;
import pg.eti.kask.jee.quickr.order.dto.GetOrderResponse;
import pg.eti.kask.jee.quickr.order.dto.GetOrdersResponse;
import pg.eti.kask.jee.quickr.order.dto.PatchOrderRequest;
import pg.eti.kask.jee.quickr.order.dto.PutOrderRequest;
import pg.eti.kask.jee.quickr.order.service.OrderService;
import pg.eti.kask.jee.quickr.order.service.VenueService;
import pg.eti.kask.jee.quickr.user.service.UserService;

import java.util.UUID;

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
    public GetOrderResponse getOrder(UUID id) {
        return factory.returnOrderFunction().apply(orderService.find(id));
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
    public void putOrder(UUID id, PutOrderRequest req) {
        orderService.create(factory.createOrderFunction().apply(id, req));
        response.setHeader("Location", uriInfo.getBaseUriBuilder()
                .path(OrderController.class, "getOrder")
                .build(id)
                .toString());
        throw new WebApplicationException(Response.Status.CREATED);

    }

    @Override
    public void patchOrder(UUID id, PatchOrderRequest req) {
        orderService.update(factory.updateOrderFunction().apply(orderService.find(id), req));
    }

    @Override
    public void deleteOrder(UUID id) {
        orderService.delete(id);
    }
}
