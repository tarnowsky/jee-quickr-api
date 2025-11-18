package pg.eti.kask.jee.quickr.controller.impl;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import pg.eti.kask.jee.quickr.component.DtoFunctionFactory;
import pg.eti.kask.jee.quickr.dto.user.*;
import pg.eti.kask.jee.quickr.entity.User;
import pg.eti.kask.jee.quickr.service.UserService;

import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Level;

@Log
@Path("")
public class UserController implements pg.eti.kask.jee.quickr.controller.api.UserController {

    private UserService userService;
    private HttpServletResponse response;

    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;

    @Inject
    public UserController(DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.returnUsersFunction().apply(userService.findAll());
    }

    @Override
    public GetUserResponse getUserById(UUID id) {
        return userService.findById(id).map(factory.returnUserFunction()).orElseThrow(NotFoundException::new);
    }

    @Override
    public GetUserResponse getUserByLogin(String login) {
        return userService.findByLogin(login).map(factory.returnUserFunction()).orElseThrow(NotFoundException::new);
    }

    @Override
    public void putUser(UUID id, PutUserRequest req) {
        try {
            userService.create(factory.createUserFunction().apply(id, req));
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }

    @Override
    public void patchUser(UUID id, PatchUserRequest req) {
        userService.findById(id).ifPresentOrElse(
                entity -> userService.update(factory.updateUserFunction().apply(entity, req)),
                NotFoundException::new
        );
    }

    @Override
    public void deleteUser(UUID id) {
        userService.findById(id).ifPresentOrElse(u -> userService.delete(id), NotFoundException::new);
    }

    @Override
    public byte[] getUserAvatar(UUID id) {
        return userService.findById(id)
                .map(u -> userService.getAvatar(id))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putUserAvatar(UUID id, HttpServletRequest request) {
        userService.findById(id).ifPresentOrElse(
                u -> {
                    try {
                        jakarta.servlet.http.Part part = request.getPart("avatar");
                        if (part == null) {
                            throw new BadRequestException();
                        }
                        try (java.io.InputStream is = part.getInputStream()) {
                            userService.updateAvatar(id, is);
                        }
                    } catch (Exception e) {
                        throw new BadRequestException();
                    }
                },
                () -> { throw new NotFoundException(); }
        );
    }

    @Override
    public void deleteUserAvatar(UUID id) {
        userService.findById(id).ifPresentOrElse(
                u -> {
                    userService.deleteAvatar(id);
                    u.setAvatarPath(null);
                    userService.update(u);
                }, NotFoundException::new
        );
    }

    @Override
    public void putUserPasswordByUserId(UUID id, PutPasswordRequest req) {
        userService.findById(id).ifPresentOrElse(
                u -> userService.updatePassword(factory.updateUserPasswordFunction().apply(u, req)),
                NotFoundException::new
        );
    }
}
