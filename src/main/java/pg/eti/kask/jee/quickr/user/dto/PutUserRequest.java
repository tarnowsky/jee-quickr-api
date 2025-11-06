package pg.eti.kask.jee.quickr.user.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@SuperBuilder
public class PutUserRequest {
    private String email;
    private String login;
    private String password;
    private LocalDate birthDate;
}
