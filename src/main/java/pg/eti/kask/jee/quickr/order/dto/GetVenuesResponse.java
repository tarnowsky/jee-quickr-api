package pg.eti.kask.jee.quickr.order.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class GetVenuesResponse {

    @Data
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Builder
    public static class Venue {
        private UUID id;
        private String name;
    }

    @Singular
    List<Venue> venues;
}
