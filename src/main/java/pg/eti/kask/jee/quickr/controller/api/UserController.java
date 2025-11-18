package pg.eti.kask.jee.quickr.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pg.eti.kask.jee.quickr.dto.user.*;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface UserController {

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    GetUsersResponse getUsers();

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUserResponse getUserById(@PathParam("id") UUID id);

    @GET
    @Path("/users/login/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUserResponse getUserByLogin(@PathParam("login") String login);

    @PUT
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void putUser(@PathParam("id") UUID id, PutUserRequest req);

    @PATCH
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void patchUser(@PathParam("id") UUID id, PatchUserRequest req);

    @DELETE
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void deleteUser(@PathParam("id") UUID id);

    @GET
    @Path("/users/{id}/avatar")
    @Produces("image/png")
    byte[] getUserAvatar(@PathParam("id") UUID id);

    @PUT
    @Path("/users/{id}/avatar")
    @Produces(MediaType.APPLICATION_JSON)
    void putUserAvatar(@PathParam("id") UUID id, HttpServletRequest avatar);

    @DELETE
    @Path("/users/{id}/avatar")
    @Produces(MediaType.APPLICATION_JSON)
    void deleteUserAvatar(@PathParam("id") UUID id);

    @PUT
    @Path("/users/{id}/passwd")
    @Produces(MediaType.APPLICATION_JSON)
    void putUserPasswordByUserId(@PathParam("id") UUID id, PutPasswordRequest req);
}
