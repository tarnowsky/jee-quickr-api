package pg.eti.kask.jee.quickr.user.controller.api;

import pg.eti.kask.jee.quickr.user.dto.GetUserResponse;
import pg.eti.kask.jee.quickr.user.dto.GetUsersResponse;
import pg.eti.kask.jee.quickr.user.dto.PatchUserRequest;
import pg.eti.kask.jee.quickr.user.dto.PutUserRequest;

import java.util.UUID;

public interface UserController {
    GetUsersResponse getUsersResponse();
    GetUserResponse getUserResponse(UUID id);
    void putUserRequest(UUID id, PutUserRequest req);
    void patchUserRequest(UUID id, PatchUserRequest req);
    void deleteUser(UUID id);
}
