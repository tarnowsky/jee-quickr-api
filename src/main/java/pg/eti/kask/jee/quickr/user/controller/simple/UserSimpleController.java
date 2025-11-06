package pg.eti.kask.jee.quickr.user.controller.simple;

import pg.eti.kask.jee.quickr.component.DtoFunctionFactory;
import pg.eti.kask.jee.quickr.controller.servlet.exception.BadRequestException;
import pg.eti.kask.jee.quickr.controller.servlet.exception.IdNotUniqueException;
import pg.eti.kask.jee.quickr.controller.servlet.exception.NotFoundException;
import pg.eti.kask.jee.quickr.user.controller.api.UserController;
import pg.eti.kask.jee.quickr.user.dto.GetUserResponse;
import pg.eti.kask.jee.quickr.user.dto.GetUsersResponse;
import pg.eti.kask.jee.quickr.user.dto.PatchUserRequest;
import pg.eti.kask.jee.quickr.user.dto.PutUserRequest;
import pg.eti.kask.jee.quickr.user.service.UserService;

import java.util.UUID;

public class UserSimpleController implements UserController {
    private final UserService service;
    private final DtoFunctionFactory factory;

    public UserSimpleController(UserService userService, DtoFunctionFactory factory) {
        this.service = userService;
        this.factory = factory;
    }

    @Override
    public GetUsersResponse getUsersResponse() {
        return factory.returnUsersFunction().apply(service.findAll());
    }

    @Override
    public GetUserResponse getUserResponse(UUID id) {
        return factory.returnUserFunction().apply(service.find(id));
    }

    @Override
    public void putUserRequest(UUID id, PutUserRequest req) {
        service.create(factory.createUserFunction().apply(id, req));
    }

    @Override
    public void patchUserRequest(UUID id, PatchUserRequest req) {
        service.update(factory.updateUserFunction().apply(service.find(id), req));
    }

    @Override
    public void deleteUser(UUID id) {
        service.delete(id);
    }
}
