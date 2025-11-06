package pg.eti.kask.jee.quickr.order.dto;

import lombok.*;
import pg.eti.kask.jee.quickr.order.entity.Order;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class GetOrdersResponse {

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Builder
    public static class Order {
        private UUID id;
        private Double price;
    }

    @Singular
    private List<Order> orders;
}
