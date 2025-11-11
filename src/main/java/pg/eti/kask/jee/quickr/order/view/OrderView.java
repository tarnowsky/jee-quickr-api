package pg.eti.kask.jee.quickr.order.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import pg.eti.kask.jee.quickr.component.ModelFunctionFactory;
import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.model.OrderModel;
import pg.eti.kask.jee.quickr.order.service.OrderService;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@ViewScoped
@Named
public class OrderView implements Serializable {

    private final OrderService service;
    private final ModelFunctionFactory factory;

    @Inject
    public OrderView(OrderService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Setter
    @Getter
    private UUID id;

    @Getter
    private OrderModel order;

    public void init() throws IOException {
        try {
            Order order = service.find(id);
            this.order = factory.orderToModel().apply(order);
        } catch (NotFoundException ex) {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(
                    HttpServletResponse.SC_NOT_FOUND, "Order not found"
            );
        }
    }
}
