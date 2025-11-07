package pg.eti.kask.jee.quickr.order.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pg.eti.kask.jee.quickr.component.DtoFunctionFactory;
import pg.eti.kask.jee.quickr.order.controller.api.OrderController;
import pg.eti.kask.jee.quickr.order.dto.GetOrderResponse;
import pg.eti.kask.jee.quickr.order.dto.GetOrdersResponse;
import pg.eti.kask.jee.quickr.order.dto.PatchOrderRequest;
import pg.eti.kask.jee.quickr.order.dto.PutOrderRequest;
import pg.eti.kask.jee.quickr.order.service.OrderService;

import java.util.UUID;

@RequestScoped
public class OrderSimpleController implements OrderController {

    private final OrderService service;
    private final DtoFunctionFactory factory;

    @Inject
    public OrderSimpleController(OrderService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetOrderResponse getOrderResponse(UUID id) {
        return factory.returnOrderFunction().apply(service.find(id));
    }

    @Override
    public GetOrdersResponse getOrdersResponse() {
        return factory.returnOrdersFunction().apply(service.findAll());
    }

    @Override
    public void putOrderRequest(UUID id, PutOrderRequest req) {
        service.create(factory.createOrderFunction().apply(id, req));
    }

    @Override
    public void patchOrderRequest(UUID id, PatchOrderRequest req) {
        service.update(factory.updateOrderFunction().apply(service.find(id), req));
    }

    @Override
    public void deleteOrderRequest(UUID id) {
        service.delete(id);
    }




}
