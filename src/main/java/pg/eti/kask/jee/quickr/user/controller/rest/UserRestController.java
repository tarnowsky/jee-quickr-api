package pg.eti.kask.jee.quickr.user.controller.rest;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import pg.eti.kask.jee.quickr.component.DtoFunctionFactory;
import pg.eti.kask.jee.quickr.user.controller.api.UserController;
import pg.eti.kask.jee.quickr.user.dto.*;
import pg.eti.kask.jee.quickr.user.entity.User;
import pg.eti.kask.jee.quickr.user.service.UserAvatarService;
import pg.eti.kask.jee.quickr.user.service.UserService;

import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Level;

@Log
@Path("")
public class UserRestController implements UserController {

    private UserService userService;
    private UserAvatarService avatarService;
    private HttpServletResponse response;

    private final DtoFunctionFactory factory;
    private final UriInfo uriInfo;

    @Inject
    public UserRestController(DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @EJB
    public void setAvatarService(UserAvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public GetUsersResponse getUsersResponse() {
        return factory.returnUsersFunction().apply(userService.findAll());
    }

    @Override
    public GetUserResponse getUserResponse(UUID id) {
        return factory.returnUserFunction().apply(userService.find(id));
    }

    @Override
    public void putUserRequest(UUID id, PutUserRequest req) {
        try {
            userService.create(factory.createUserFunction().apply(id, req));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(UserController.class, "getUser")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }

    }

    @Override
    public void patchUserRequest(UUID id, PatchUserRequest req) {
        try {
            userService.update(factory.updateUserFunction().apply(userService.find(id), req));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(UserController.class, "getUser")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.NO_CONTENT);
        } catch (NotFoundException ex) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

    }

    @Override
    public void deleteUser(UUID id) {
        try {
            userService.delete(id);
            throw new WebApplicationException(Response.Status.NO_CONTENT);
        } catch (NotFoundException ex) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @Override
    public byte[] getUserAvatar(UUID id) {
        return avatarService.getAvatar(id);
    }

    @Override
    public void putUserAvatar(UUID id, InputStream avatar) {
        avatarService.saveAvatar(id, avatar);
        User user = userService.find(id);
        user.setAvatarPath(avatarService.getAvatarPath(id));
        userService.update(user);
    }

    @Override
    public void deleteUserAvatar(UUID id) {
        avatarService.deleteAvatar(id);
        User user = userService.find(id);
        user.setAvatarPath(null);
        userService.update(user);
    }

    @Override
    public void putUserPasswordByUserId(UUID id, PutPasswordRequest req) {
        userService.updatePassword(factory.updateUserPasswordFunction().apply(userService.find(id), req));
    }
}
