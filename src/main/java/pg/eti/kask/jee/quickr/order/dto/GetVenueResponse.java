package pg.eti.kask.jee.quickr.order.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class GetVenueResponse {

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Builder
    public static class Order {
        private UUID id;
        private Double price;
    }

    private UUID id;
    private String name;
    private String venueCategory;
    @Singular
    List<Order> orders;

}
