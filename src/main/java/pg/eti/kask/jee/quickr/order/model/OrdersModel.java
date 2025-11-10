package pg.eti.kask.jee.quickr.order.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class OrdersModel {

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Builder
    public static class Order {
        private UUID id;
        private Double price;
    }

    @Singular
    List<Order> orders;
}
