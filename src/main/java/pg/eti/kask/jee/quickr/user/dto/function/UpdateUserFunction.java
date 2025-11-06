package pg.eti.kask.jee.quickr.user.dto.function;

import pg.eti.kask.jee.quickr.user.dto.PatchUserRequest;
import pg.eti.kask.jee.quickr.user.entity.User;

import java.util.function.BiFunction;

public class UpdateUserFunction implements BiFunction<User, PatchUserRequest, User> {

    @Override
    public User apply(User user, PatchUserRequest req) {
        return User.builder()
                .email(req.getEmail())
                .login(req.getLogin())
                .birthDate(req.getBirthDate())
                .id(user.getId())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
