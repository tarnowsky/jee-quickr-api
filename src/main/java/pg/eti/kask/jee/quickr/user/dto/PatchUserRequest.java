package pg.eti.kask.jee.quickr.user.dto;

import lombok.*;
import pg.eti.kask.jee.quickr.user.entity.UserRole;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PatchUserRequest {
    private String email;
    private String login;
    private LocalDate birthDate;
}
