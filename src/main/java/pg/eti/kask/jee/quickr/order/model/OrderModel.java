package pg.eti.kask.jee.quickr.order.model;

import lombok.*;


@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class OrderModel {
    private String id;
    private Double price;
    private String venueName;
    private String userLogin;
    private String orderDate;
}
