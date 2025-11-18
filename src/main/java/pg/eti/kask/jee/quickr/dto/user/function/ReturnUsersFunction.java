package pg.eti.kask.jee.quickr.dto.user.function;

import pg.eti.kask.jee.quickr.dto.user.GetUsersResponse;
import pg.eti.kask.jee.quickr.entity.User;

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
