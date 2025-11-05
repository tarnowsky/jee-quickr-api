package pg.eti.kask.jee.quickr.user.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@SuperBuilder
public class User implements Serializable {
    private UUID id;
    private String email;
    private String login;
    @ToString.Exclude
    private String password;
    private UserRole role;
    private LocalDate birthDate;
}
