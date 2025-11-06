package pg.eti.kask.jee.quickr.user.dto.function;

import pg.eti.kask.jee.quickr.user.dto.PutPasswordRequest;
import pg.eti.kask.jee.quickr.user.entity.User;

import java.util.function.BiFunction;

public class UpdateUserPasswordFunction implements BiFunction<User, PutPasswordRequest, User> {

    @Override
    public User apply(User user, PutPasswordRequest req) {
        return User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .login(user.getLogin())
                .role(user.getRole())
                .birthDate(user.getBirthDate())
                .avatarPath(user.getAvatarPath())
                .password(req.getPassword())
                .build();

    }
}
