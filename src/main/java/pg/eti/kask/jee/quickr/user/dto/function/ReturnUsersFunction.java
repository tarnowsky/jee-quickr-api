package pg.eti.kask.jee.quickr.user.dto.function;

import pg.eti.kask.jee.quickr.user.dto.GetUserResponse;
import pg.eti.kask.jee.quickr.user.dto.GetUsersResponse;
import pg.eti.kask.jee.quickr.user.entity.User;

import java.util.List;
import java.util.function.Function;

public class ReturnUsersFunction implements Function<List<User>, GetUsersResponse> {

    @Override
    public GetUsersResponse apply(List<User> users) {
        return GetUsersResponse.builder()
                .users(users.stream()
                        .map(user -> GetUsersResponse.User.builder()
                                .id(user.getId())
                                .login(user.getLogin())
                                .build())
                        .toList())
                .build();
    }
}
