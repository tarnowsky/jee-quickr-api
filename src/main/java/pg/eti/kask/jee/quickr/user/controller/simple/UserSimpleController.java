package pg.eti.kask.jee.quickr.user.controller.simple;

import jakarta.ws.rs.BadRequestException;
import pg.eti.kask.jee.quickr.component.DtoFunctionFactory;
import pg.eti.kask.jee.quickr.user.controller.api.UserController;
import pg.eti.kask.jee.quickr.user.dto.GetUserResponse;
import pg.eti.kask.jee.quickr.user.dto.GetUsersResponse;
import pg.eti.kask.jee.quickr.user.dto.PatchUserRequest;
import pg.eti.kask.jee.quickr.user.dto.PutUserRequest;
import pg.eti.kask.jee.quickr.user.exceptions.UserIdNotUniqueException;
import pg.eti.kask.jee.quickr.user.exceptions.UserNotFoundException;
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
        try {
            return factory.returnUserFunction().apply(service.find(id));
        } catch (UserNotFoundException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void putUserRequest(UUID id, PutUserRequest req) {
        try {
            service.create(factory.createUserFunction().apply(id, req));
        } catch (UserIdNotUniqueException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchUserRequest(UUID id, PatchUserRequest req) {
        try {
            service.update(factory.updateUserFunction().apply(service.find(id), req));
        } catch (UserNotFoundException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void deleteUser(UUID id) {
        try {
            service.delete(id);
        } catch (UserNotFoundException ex) {
            throw new BadRequestException(ex);
        }
    }
}
