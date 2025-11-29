package pg.eti.kask.jee.quickr.model.order;

import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class OrderModel {
    private String id;
    private Double price;
    private String name;
    private Integer itemCount;
    private String venueName;
    private String userLogin;
    private String orderDate;
    private java.time.LocalDateTime creationDateTime;
    private java.time.LocalDateTime modificationDateTime;
    private Long version;
}
