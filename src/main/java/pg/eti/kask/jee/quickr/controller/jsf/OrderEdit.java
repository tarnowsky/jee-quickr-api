package pg.eti.kask.jee.quickr.controller.jsf;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import pg.eti.kask.jee.quickr.component.ModelFunctionFactory;
import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.model.order.OrderEditModel;
import pg.eti.kask.jee.quickr.service.OrderService;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@ViewScoped
@Named
public class OrderEdit implements Serializable {

    private OrderService orderService;
    private final ModelFunctionFactory factory;

    @EJB
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Inject
    public OrderEdit(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @Getter
    @Setter
    private UUID id;

    @Getter
    private OrderEditModel order;

    public void init() throws IOException {
        try {
            // Check if we are returning from a failed save (PRG)
            OrderEditModel failedOrder = (OrderEditModel) FacesContext.getCurrentInstance().getExternalContext()
                    .getFlash().get("failedOrder");

            Order orderEntity = orderService.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
            OrderEditModel currentDbState = factory.orderToEditModel().apply(orderEntity);

            if (failedOrder != null) {
                // Restore user's input
                this.order = failedOrder;
                // Set DB state for comparison
                this.dbOrder = currentDbState;
            } else {
                // Normal load
                this.order = currentDbState;
            }
        } catch (NotFoundException ex) {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(
                    HttpServletResponse.SC_NOT_FOUND, "Order not found");
        }
    }

    @Getter
    private OrderEditModel dbOrder;

    public String reloadData() {
        return "order_edit?faces-redirect=true&includeViewParams=true";
    }

    public String getVenueId() {
        return orderService.findById(id).map(o -> o.getVenue().getId().toString()).orElse("");
    }

    public String saveAction() {
        try {
            orderService.update(factory.updateOrderWithModel().apply(orderService.findById(id).get(), order));
            return "order_view?faces-redirect=true&includeViewParams=true";
        } catch (pg.eti.kask.jee.quickr.exception.OrderOptimisticLockException e) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage(null,
                    new jakarta.faces.application.FacesMessage(
                            jakarta.faces.application.FacesMessage.SEVERITY_ERROR,
                            "Data has been modified by another user.", null));

            // Save state to Flash
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("failedOrder", order);

            return "order_edit?faces-redirect=true&includeViewParams=true";
        } catch (jakarta.persistence.OptimisticLockException | jakarta.ejb.EJBException e) {
            if (e instanceof jakarta.persistence.OptimisticLockException ||
                    (e.getCause() instanceof jakarta.persistence.OptimisticLockException)) {

                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                FacesContext.getCurrentInstance().addMessage(null,
                        new jakarta.faces.application.FacesMessage(
                                jakarta.faces.application.FacesMessage.SEVERITY_ERROR,
                                "Data has been modified by another user.", null));

                // Save state to Flash
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("failedOrder", order);

                return "order_edit?faces-redirect=true&includeViewParams=true";
            }
            throw e;
        }
    }
}
