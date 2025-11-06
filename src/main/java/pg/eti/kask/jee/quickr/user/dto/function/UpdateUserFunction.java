package pg.eti.kask.jee.quickr.user.dto.function;

import pg.eti.kask.jee.quickr.user.dto.PatchUserRequest;
import pg.eti.kask.jee.quickr.user.entity.User;

import java.util.function.BiFunction;

public class UpdateUserFunction implements BiFunction<User, PatchUserRequest, User> {

    @Override
    public User apply(User user, PatchUserRequest req) {
        return User.builder()
                .id(user.getId())
                .email(req.getEmail() != null && !req.getEmail().isBlank() ? req.getEmail() : user.getEmail())
                .login(req.getLogin() != null && !req.getLogin().isBlank() ? req.getLogin() : user.getLogin())
                .birthDate(req.getBirthDate() != null ? req.getBirthDate() : user.getBirthDate())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
