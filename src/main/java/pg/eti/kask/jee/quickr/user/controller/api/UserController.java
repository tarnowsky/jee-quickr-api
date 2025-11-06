package pg.eti.kask.jee.quickr.user.controller.api;

import pg.eti.kask.jee.quickr.user.dto.*;

import java.io.InputStream;
import java.util.UUID;

public interface UserController {
    GetUsersResponse getUsersResponse();
    GetUserResponse getUserResponse(UUID id);
    void putUserRequest(UUID id, PutUserRequest req);
    void patchUserRequest(UUID id, PatchUserRequest req);
    void deleteUser(UUID id);

    byte[] getUserAvatar(UUID id);
    void putUserAvatar(UUID id, InputStream avatar);
    void deleteUserAvatar(UUID id);
}
