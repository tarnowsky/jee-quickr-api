package pg.eti.kask.jee.quickr.order.view;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import pg.eti.kask.jee.quickr.component.ModelFunctionFactory;
import pg.eti.kask.jee.quickr.order.entity.Order;
import pg.eti.kask.jee.quickr.order.entity.Venue;
import pg.eti.kask.jee.quickr.order.model.OrderCreateModel;
import pg.eti.kask.jee.quickr.order.model.VenueModel;
import pg.eti.kask.jee.quickr.order.service.OrderService;
import pg.eti.kask.jee.quickr.order.service.VenueService;
import pg.eti.kask.jee.quickr.user.service.UserService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class OrderCreate implements Serializable {

    private final OrderService orderService;
    private final VenueService venueService;
    private final UserService userService;

    private final ModelFunctionFactory factory;

    @Getter
    private OrderCreateModel order;

    @Getter
    @Setter
    private UUID venueId;

    private final Conversation conversation;

    @Inject
    public OrderCreate(
            OrderService orderService, VenueService venueService, UserService userService, ModelFunctionFactory factory, Conversation conversation) {
        this.orderService = orderService;
        this.venueService = venueService;
        this.userService = userService;
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
                    .venue(factory.venueToModel().apply(venueService.find(venueId)))
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
        Venue venueEntity = venueService.find(venueId);
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
