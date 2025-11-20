package pg.eti.kask.jee.quickr.controller.impl;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
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
import pg.eti.kask.jee.quickr.dto.order.*;
import pg.eti.kask.jee.quickr.entity.enums.UserRoles;
import pg.eti.kask.jee.quickr.service.OrderService;
import pg.eti.kask.jee.quickr.service.UserService;
import pg.eti.kask.jee.quickr.service.VenueService;

import java.util.UUID;
import java.util.logging.Level;

@Log
@Path("")
@RolesAllowed(UserRoles.USER)
public class OrderController implements pg.eti.kask.jee.quickr.controller.api.OrderController {

    private OrderService orderService;
    private HttpServletResponse response;
    private SecurityContext securityContext;
    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;

    @EJB
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public void setSecurityContext(@SuppressWarnings("CdiInjectionPointsInspection") jakarta.security.enterprise.SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @Inject
    public OrderController(DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetOrdersResponse getOrders() {
        return factory.returnOrdersFunction().apply(orderService.findAllForCallerPrincipal());
    }

    @Override
    public GetOrderResponse getOrder(UUID orderId) {
        return orderService.findById(orderId)
                .map(factory.returnOrderFunction())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetOrdersResponse getVenueOrders(UUID venueId) {
        return orderService.findAllByVenue(venueId)
                .map(factory.returnOrdersFunction())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetOrdersResponse getUserOrders(UUID userId) {
        return orderService.findAllByUser(userId)
                .map(factory.returnOrdersFunction())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putOrder(UUID orderId, PutOrderRequest req) {

        try {
            OrderService svc = orderService;
            if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
                orderService.create(factory.createOrderFunction().apply(orderId, req));
            } else {
                svc.createForCallerPrincipal(factory.createOrderFunction().apply(orderId, req));
            }

            String location = uriInfo.getBaseUriBuilder()
                    .path("api")
                    .path("orders")
                    .path(orderId.toString())
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
    public void putOrderWithVenueId(UUID venueId, UUID orderId, PutOrderWithVenueRequest req) {
        try {
            if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
                orderService.create(
                        factory.createOrderWithVenueFunction().apply(orderId, venueId, req)
                );
            } else {
                orderService.createForCallerPrincipal(
                        factory.createOrderWithVenueFunction().apply(orderId, venueId, req)
                );
            }

            String location = uriInfo.getBaseUriBuilder()
                    .path("api")
                    .path("firearms")
                    .path(orderId.toString())
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
    public void patchOrder(UUID orderId, PatchOrderRequest req) {
        orderService.findById(orderId).ifPresentOrElse(
                entity -> orderService.update(factory.updateOrderFunction().apply(entity, req)),
                NotFoundException::new
        );
    }

    @Override
    public void deleteOrder(UUID orderId) {
        orderService.findById(orderId).ifPresentOrElse(
                o -> orderService.delete(orderId),
                NotFoundException::new
        );
    }
}
