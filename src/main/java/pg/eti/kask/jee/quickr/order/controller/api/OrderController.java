package pg.eti.kask.jee.quickr.order.controller.api;

import pg.eti.kask.jee.quickr.order.dto.GetOrderResponse;
import pg.eti.kask.jee.quickr.order.dto.GetOrdersResponse;
import pg.eti.kask.jee.quickr.order.dto.PatchOrderRequest;
import pg.eti.kask.jee.quickr.order.dto.PutOrderRequest;

import java.util.UUID;

public interface OrderController {
    GetOrderResponse getOrderResponse(UUID id);
    GetOrdersResponse getOrdersResponse();
    void putOrderRequest(UUID id, PutOrderRequest req);
    void patchOrderRequest(UUID id, PatchOrderRequest req);
    void deleteOrderRequest(UUID id);
}
