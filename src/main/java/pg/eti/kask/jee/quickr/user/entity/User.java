package pg.eti.kask.jee.quickr.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pg.eti.kask.jee.quickr.order.entity.Order;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    private UUID id;

    private String email;
    private String login;
    @ToString.Exclude
    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Singular
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Order> orders;

    @ToString.Exclude
    private String avatarPath;
}
