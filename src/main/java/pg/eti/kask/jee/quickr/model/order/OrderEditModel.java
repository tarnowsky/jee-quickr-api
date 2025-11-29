package pg.eti.kask.jee.quickr.model.order;

import lombok.*;

import java.time.LocalDate;

import java.io.Serializable;

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
public class OrderEditModel implements Serializable {
    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotNull
    @Min(1)
    @MaxItemCount(99)
    private Integer itemCount;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    private LocalDate orderDate;
    private Long version;
    // private UUID userId;
    // private UUID venueId;
}
