package pg.eti.kask.jee.quickr.user.dto.function;

import pg.eti.kask.jee.quickr.user.dto.PutUserRequest;
import pg.eti.kask.jee.quickr.user.entity.User;

import java.util.UUID;
import java.util.function.BiFunction;

public class CreateUserFunction implements BiFunction<UUID, PutUserRequest, User> {

    @Override
    public User apply(UUID id, PutUserRequest req) {
        return User.builder()
                .id(id)
                .email(req.getEmail())
                .login(req.getLogin())
                .password(req.getPassword())
                .birthDate(req.getBirthDate())
                .avatarPath(req.getAvatarPath())
                .build();
    }

}
