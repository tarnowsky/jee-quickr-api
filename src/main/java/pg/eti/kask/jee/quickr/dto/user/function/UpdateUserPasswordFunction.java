package pg.eti.kask.jee.quickr.dto.user.function;

import pg.eti.kask.jee.quickr.dto.user.PutPasswordRequest;
import pg.eti.kask.jee.quickr.entity.User;

import java.util.function.BiFunction;

public class UpdateUserPasswordFunction implements BiFunction<User, PutPasswordRequest, User> {

    @Override
    public User apply(User user, PutPasswordRequest req) {
        return User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .login(user.getLogin())
                .roles(user.getRoles())
                .birthDate(user.getBirthDate())
                .avatarPath(user.getAvatarPath())
                .password(req.getPassword())
                .build();

    }
}
