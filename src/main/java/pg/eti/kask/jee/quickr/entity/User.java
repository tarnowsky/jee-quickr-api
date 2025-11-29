package pg.eti.kask.jee.quickr.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    private String login;

    @Column(nullable = false, unique = true)
    private String email;

    @ToString.Exclude
    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Singular
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Order> orders;

    @Singular
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String avatarPath;
}
