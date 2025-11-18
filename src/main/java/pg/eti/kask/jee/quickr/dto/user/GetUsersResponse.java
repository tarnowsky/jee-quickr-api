package pg.eti.kask.jee.quickr.dto.user;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUsersResponse {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class User {
        private UUID id;
        private String login;
    }

    @Singular
    private List<User> users;
}
