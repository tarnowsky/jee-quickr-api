package pg.eti.kask.jee.quickr.dto.user.function;

import pg.eti.kask.jee.quickr.dto.user.PatchUserRequest;
import pg.eti.kask.jee.quickr.entity.User;

import java.util.function.BiFunction;

public class UpdateUserFunction implements BiFunction<User, PatchUserRequest, User> {

    @Override
    public User apply(User user, PatchUserRequest req) {
        return User.builder()
                .id(user.getId())
                .email(req.getEmail() != null && !req.getEmail().isBlank() ? req.getEmail() : user.getEmail())
                .login(req.getLogin() != null && !req.getLogin().isBlank() ? req.getLogin() : user.getLogin())
                .birthDate(req.getBirthDate() != null ? req.getBirthDate() : user.getBirthDate())
                .avatarPath(req.getAvatarPath() != null && !req.getAvatarPath().isBlank()
                        ? req.getAvatarPath()
                        : user.getAvatarPath())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }
}
