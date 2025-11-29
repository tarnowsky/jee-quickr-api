package pg.eti.kask.jee.quickr.model.order;

import lombok.*;
import pg.eti.kask.jee.quickr.model.venue.VenueModel;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import pg.eti.kask.jee.quickr.validator.MaxItemCount;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class OrderCreateModel {
    private UUID id;

    @NotNull
    @Positive
    private Double price;

    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotNull
    @Min(1)
    @MaxItemCount(99)
    private Integer itemCount;

    private VenueModel venue;
    private UUID userId;

    @NotNull
    private LocalDate orderDate;
}
