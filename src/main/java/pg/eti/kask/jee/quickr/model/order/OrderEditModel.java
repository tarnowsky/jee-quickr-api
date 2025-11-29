package pg.eti.kask.jee.quickr.model.order;

import lombok.*;

import java.time.LocalDate;

import java.io.Serializable;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class OrderEditModel implements Serializable {
    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(min = 3, max = 30)
    private String name;

    @jakarta.validation.constraints.NotNull
    @jakarta.validation.constraints.Min(1)
    @pg.eti.kask.jee.quickr.validator.MaxItemCount(99)
    private Integer itemCount;

    @jakarta.validation.constraints.NotNull
    @jakarta.validation.constraints.Positive
    private Double price;

    @jakarta.validation.constraints.NotNull
    private LocalDate orderDate;
    private Long version;
    // private UUID userId;
    // private UUID venueId;
}
