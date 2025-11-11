package pg.eti.kask.jee.quickr.order.view;


import jakarta.faces.annotation.View;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import pg.eti.kask.jee.quickr.component.ModelFunctionFactory;
import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.model.OrderEditModel;
import pg.eti.kask.jee.quickr.order.service.OrderService;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@ViewScoped
@Named
public class OrderEdit implements Serializable {

    private final OrderService orderService;
    private final ModelFunctionFactory factory;

    @Inject
    public OrderEdit(OrderService orderService, ModelFunctionFactory factory) {
        this.orderService = orderService;
        this.factory = factory;
    }

    @Getter
    @Setter
    private UUID id;

    @Getter
    private OrderEditModel order;


    public void init() throws IOException {
        try {
            Order order = orderService.find(id);
            this.order = factory.orderToEditModel().apply(order);
        } catch (NotFoundException ex) {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(
                    HttpServletResponse.SC_NOT_FOUND, "Order not found"
            );
        }
    }

    public String saveAction() {
        orderService.update(factory.updateOrderWithModel().apply(orderService.find(id), order));
        return "order_view?faces-redirect=true&includeViewParams=true";
    }
}
