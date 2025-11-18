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
import pg.eti.kask.jee.quickr.entity.Venue;
import pg.eti.kask.jee.quickr.model.order.OrderCreateModel;
import pg.eti.kask.jee.quickr.service.OrderService;
import pg.eti.kask.jee.quickr.service.VenueService;
import pg.eti.kask.jee.quickr.service.UserService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
            ModelFunctionFactory factory, Conversation conversation) {
        this.factory = factory;
        this.conversation = conversation;
    }

    public void init() {

        assert conversation != null;
        assert factory != null;
        assert venueService != null;

        if (conversation.isTransient()) {
            assert userService != null;
            order = OrderCreateModel.builder()
                    .id(UUID.randomUUID())
                    .venue(venueService.findById(venueId).map(factory.venueToModel()).orElseThrow(NotFoundException::new))
                    .userId(userService.findAll().stream().findAny().get().getId())
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
        Venue venueEntity = venueService.findById(venueId).get();
        orderService.create(newOrder);

        List<Order> mutableOrders = new ArrayList<>(venueEntity.getOrders());
        mutableOrders.add(newOrder);
        venueEntity.setOrders(mutableOrders);
        venueService.update(venueEntity);

        conversation.end();
        return "/venue/venue_view?id=%s&faces-redirect=true".formatted(venueId);
    }

    public String cancelAction() {
        assert conversation != null;

        conversation.end();
        return "/venue/venue_list?faces-redirect=true";
    }

}
