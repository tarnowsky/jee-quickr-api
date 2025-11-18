package pg.eti.kask.jee.quickr.dto.user.function;

import pg.eti.kask.jee.quickr.dto.user.GetUserResponse;
import pg.eti.kask.jee.quickr.entity.User;

import java.util.function.Function;

public class ReturnUserFunction implements Function<User, GetUserResponse> {
    @Override
    public GetUserResponse apply(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .login(user.getLogin())
                .birthDate(user.getBirthDate())
                .build();
    }
}
