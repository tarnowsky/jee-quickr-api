package pg.eti.kask.jee.quickr.controller.jsf;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pg.eti.kask.jee.quickr.component.ModelFunctionFactory;
import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.model.order.OrderModel;
import pg.eti.kask.jee.quickr.service.OrderService;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@ViewScoped
@Named
public class OrderView implements Serializable {

    private OrderService service;
    private final ModelFunctionFactory factory;

    @EJB
    public void setService(OrderService service) {
        this.service = service;
    }

    @Inject
    public OrderView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @Setter
    @Getter
    private UUID id;

    @Getter
    private OrderModel order;

    public void init() throws IOException {
        java.util.Optional<Order> orderOptional = service.findById(id);
        if (orderOptional.isPresent()) {
            this.order = factory.orderToModel().apply(orderOptional.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(
                    HttpServletResponse.SC_NOT_FOUND, "Order not found");
        }
    }
}
