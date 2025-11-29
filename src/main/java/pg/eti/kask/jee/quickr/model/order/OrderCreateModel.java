package pg.eti.kask.jee.quickr.model.order;

import lombok.*;
import pg.eti.kask.jee.quickr.model.venue.VenueModel;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class OrderCreateModel {
    private UUID id;

    @jakarta.validation.constraints.NotNull
    @jakarta.validation.constraints.Positive
    private Double price;

    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(min = 3, max = 30)
    private String name;

    @jakarta.validation.constraints.NotNull
    @jakarta.validation.constraints.Min(1)
    @pg.eti.kask.jee.quickr.validator.MaxItemCount(99)
    private Integer itemCount;

    private VenueModel venue;
    private UUID userId;

    @jakarta.validation.constraints.NotNull
    private LocalDate orderDate;
}
