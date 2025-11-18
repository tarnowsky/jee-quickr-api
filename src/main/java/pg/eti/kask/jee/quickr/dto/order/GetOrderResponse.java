package pg.eti.kask.jee.quickr.dto.order;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class GetOrderResponse {

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Builder
    public static class Venue {
        private UUID id;
        private String name;
    }

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Builder
    public static class User {
        private UUID id;
        private String login;
    }

    private UUID id;
    private Double price;
    private Venue venue;
    private User user;
    private LocalDate orderDate;
}
