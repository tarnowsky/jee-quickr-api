package pg.eti.kask.jee.quickr.user.controller.resr;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
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
import pg.eti.kask.jee.quickr.user.service.UserService;

import java.io.InputStream;
import java.util.UUID;

@Log
@Path("")
public class UserRestController implements UserController {

    private UserService userService;
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
        userService.create(factory.createUserFunction().apply(id, req));
        response.setHeader("Location", uriInfo.getBaseUriBuilder()
                .path(UserController.class, "getUser")
                .build(id)
                .toString());
        throw new WebApplicationException(Response.Status.CREATED);
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
        return new byte[0];
    }

    @Override
    public void putUserAvatar(UUID id, InputStream avatar) {

    }

    @Override
    public void deleteUserAvatar(UUID id) {

    }

    @Override
    public void putUserPasswordByUserId(UUID id, PutPasswordRequest req) {

    }
}
