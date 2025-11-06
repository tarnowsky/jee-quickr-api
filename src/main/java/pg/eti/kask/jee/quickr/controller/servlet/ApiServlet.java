package pg.eti.kask.jee.quickr.controller.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pg.eti.kask.jee.quickr.user.controller.api.UserController;
import pg.eti.kask.jee.quickr.user.dto.PatchUserRequest;
import pg.eti.kask.jee.quickr.user.dto.PutUserRequest;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@MultipartConfig(maxFileSize = 200 * 1024)
@WebServlet(urlPatterns = ApiServlet.Paths.API +  "/*")
public class ApiServlet extends HttpServlet {

    public static final class Paths {
        public static final String API = "/api";
    }

    public static final class Patterns {
        private static final Pattern UUID = Pattern.
                compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
        private static final Pattern USERS = Pattern.compile("/users/?");
        private static final Pattern USER = Pattern.compile("/users/(%s)".formatted(UUID.pattern()));
        private static final Pattern USER_AVATAR = Pattern.compile("/users/(%s)/avatar".formatted(UUID.pattern()));
    }

    private UserController userController;

    private final Jsonb jsonb = JsonbBuilder.create();

    private static final String jsonContentType = "application/json";
    private static final String imageContentType = "image/png";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getMethod().equals("PATCH")) {
            doPatch(req, res);
        } else {
            super.service(req, res);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        userController = (UserController) getServletContext().getAttribute("userController");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USERS.pattern())) {
                res.setContentType(jsonContentType);
                res.getWriter().write(jsonb.toJson(userController.getUsersResponse()));
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                res.setContentType(jsonContentType);
                UUID id = extractUuid(Patterns.USER, path);
                res.getWriter().write(jsonb.toJson(userController.getUserResponse(id)));
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                res.setContentType(imageContentType);
                UUID id = extractUuid(Patterns.USER_AVATAR, path);
                try {
                    byte[] avatar = userController.getUserAvatar(id);
                    res.setContentLength(avatar.length);
                    res.getOutputStream().write(avatar);
                } catch (IllegalStateException e) {
                    res.sendError(HttpServletResponse.SC_NOT_FOUND, "Avatar not found");
                }
                return;
            }
        }
        res.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER.pattern())) {
                UUID id = extractUuid(Patterns.USER, path);
                userController.putUserRequest(id, jsonb.fromJson(req.getReader(), PutUserRequest.class));
                res.addHeader("Location", createUrl(req, Paths.API, "users", id.toString()));
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID id = extractUuid(Patterns.USER_AVATAR, path);
                userController.putUserAvatar(id, req.getPart("avatar").getInputStream());
                return;
            }
        }
        res.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }


    protected void doPatch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER.pattern())) {
                UUID id = extractUuid(Patterns.USER, path);
                userController.patchUserRequest(id, jsonb.fromJson(req.getReader(), PatchUserRequest.class));
                return;
            }
        }
        res.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = parseRequestPath(req);
        String servletPath = req.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER.pattern())) {
                UUID id = extractUuid(Patterns.USER, path);
                userController.deleteUser(id);
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                userController.deleteUserAvatar(uuid);
                return;
            }
        }
        res.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }


    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }
}
