package pg.eti.kask.jee.quickr.controller.jsf;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.NotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import pg.eti.kask.jee.quickr.component.ModelFunctionFactory;
import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.model.order.OrderCreateModel;
import pg.eti.kask.jee.quickr.service.OrderService;
import pg.eti.kask.jee.quickr.service.VenueService;
import pg.eti.kask.jee.quickr.service.UserService;

import java.io.Serializable;
import java.util.UUID;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class OrderCreate implements Serializable {

    private OrderService orderService;
    private VenueService venueService;
    private UserService userService;

    private final Conversation conversation;
    private final ModelFunctionFactory factory;
    private final jakarta.security.enterprise.SecurityContext securityContext;

    @Getter
    private OrderCreateModel order;

    @Getter
    @Setter
    private UUID venueId;

    @EJB
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @EJB
    public void setVenueService(VenueService venueService) {
        this.venueService = venueService;
    }

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public OrderCreate(
            ModelFunctionFactory factory,
            Conversation conversation,
            @SuppressWarnings("CdiInjectionPointsInspection") jakarta.security.enterprise.SecurityContext securityContext) {
        this.factory = factory;
        this.conversation = conversation;
        this.securityContext = securityContext;
    }

    public void init() {

        assert conversation != null;
        assert factory != null;
        assert venueService != null;

        if (conversation.isTransient()) {
            assert userService != null;
            assert securityContext != null;

            String currentUserLogin = securityContext.getCallerPrincipal().getName();
            UUID currentUserId = userService.findByLogin(currentUserLogin)
                    .orElseThrow(() -> new IllegalStateException("Current user not found"))
                    .getId();

            order = OrderCreateModel.builder()
                    .id(UUID.randomUUID())
                    .venue(venueService.findById(venueId).map(factory.venueToModel())
                            .orElseThrow(NotFoundException::new))
                    .userId(currentUserId)
                    .build();
            conversation.begin();
        }
    }

    public String saveAction() {

        assert conversation != null;
        assert factory != null;
        assert venueService != null;
        assert orderService != null;

        Order newOrder = factory.modelToOrder(userService).apply(order);

        // Use appropriate create method based on role
        if (securityContext.isCallerInRole(pg.eti.kask.jee.quickr.entity.enums.UserRoles.ADMIN)) {
            orderService.create(newOrder);
        } else {
            orderService.createForCallerPrincipal(newOrder);
        }

        // No need to manually update venue - JPA manages the bidirectional relationship

        conversation.end();
        return "/venue/user_venue_orders?venueId=%s&faces-redirect=true".formatted(venueId);
    }

    public String cancelAction() {
        assert conversation != null;

        conversation.end();
        return "/venue/venue_list?faces-redirect=true";
    }

}
