package pg.eti.kask.jee.quickr.user.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pg.eti.kask.jee.quickr.component.DtoFunctionFactory;
import pg.eti.kask.jee.quickr.controller.servlet.exception.BadRequestException;
import pg.eti.kask.jee.quickr.controller.servlet.exception.IdNotUniqueException;
import pg.eti.kask.jee.quickr.controller.servlet.exception.NotFoundException;
import pg.eti.kask.jee.quickr.order.dto.GetOrdersResponse;
import pg.eti.kask.jee.quickr.user.controller.api.UserController;
import pg.eti.kask.jee.quickr.user.dto.*;
import pg.eti.kask.jee.quickr.user.entity.User;
import pg.eti.kask.jee.quickr.user.service.UserAvatarService;
import pg.eti.kask.jee.quickr.user.service.UserService;

import java.io.InputStream;
import java.util.UUID;

@RequestScoped
public class UserSimpleController implements UserController {
    private final UserService service;
    private final UserAvatarService avatarService;
    private final DtoFunctionFactory factory;

    @Inject
    public UserSimpleController(UserService userService, UserAvatarService avatarService, DtoFunctionFactory factory) {
        this.service = userService;
        this.avatarService = avatarService;
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

    @Override
    public byte[] getUserAvatar(UUID id) {
        return avatarService.getAvatar(id);
    }

    @Override
    public void putUserAvatar(UUID id, InputStream avatar) {
        avatarService.saveAvatar(id, avatar);
        User usr = service.find(id);
        usr.setAvatarPath(avatarService.getAvatarPath(id));
        service.update(usr);
    }

    @Override
    public void deleteUserAvatar(UUID id) {
        avatarService.deleteAvatar(id);
        User usr = service.find(id);
        usr.setAvatarPath(null);
        service.update(usr);
    }

    @Override
    public GetOrdersResponse getOrdersByUserId(UUID id) {
        return factory.returnOrdersFunction().apply(service.findAllOrdersByUserId(id));
    }

    @Override
    public void putUserPasswordByUserId(UUID id, PutPasswordRequest req) {
        service.updatePassword(factory.updateUserPasswordFunction().apply(service.find(id), req));
    }
}
