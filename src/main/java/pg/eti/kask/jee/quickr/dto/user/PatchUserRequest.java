package pg.eti.kask.jee.quickr.dto.user;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PatchUserRequest {
    private String email;
    private String login;
    private LocalDate birthDate;
    private String avatarPath;
}
