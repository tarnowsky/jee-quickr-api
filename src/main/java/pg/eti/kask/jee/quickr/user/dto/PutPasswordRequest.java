package pg.eti.kask.jee.quickr.user.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PutPasswordRequest {
    private String password;
}
