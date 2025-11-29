package pg.eti.kask.jee.quickr.model.order;

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
        private String name;
        private Integer itemCount;
        private java.time.LocalDateTime creationDateTime;
        private java.time.LocalDateTime modificationDateTime;
        private Long version;
    }

    @Singular
    List<Order> orders;
}
