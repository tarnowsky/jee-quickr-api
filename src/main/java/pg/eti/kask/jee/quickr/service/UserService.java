package pg.eti.kask.jee.quickr.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import pg.eti.kask.jee.quickr.entity.User;
import pg.eti.kask.jee.quickr.entity.enums.UserRoles;
import pg.eti.kask.jee.quickr.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UserService {
    private final UserRepository userRepository;
    private final Pbkdf2PasswordHash passwordHash;
    private final String avatarDirectory;

    @Inject
    public UserService(
            UserRepository userRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash,
            @Named("avatarDirectory") String avatarDirectory) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
        this.avatarDirectory = avatarDirectory;
    }

    @RolesAllowed(UserRoles.ADMIN)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @PermitAll
    public void create(User user) {
        validateUser(user);

        if (userRepository.existsByLogin(user.getLogin())) {
            throw new IllegalArgumentException("User with login '" + user.getLogin() + "' already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with email '" + user.getEmail() + "' already exists");
        }

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            String p = user.getPassword();
            if (!p.startsWith("$pbkdf2$") && !p.startsWith("{PBKDF2}")) {
                String hashed = passwordHash.generate(p.toCharArray());
                user.setPassword(hashed);
            }
        }

        if (user.getRoles().isEmpty()) {
            user.setRoles(List.of(UserRoles.USER));
        }

        userRepository.create(user);

    }

    @RolesAllowed(UserRoles.ADMIN)
    public void update(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User ID cannot be null for update operation");
        }

        if (!userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("User with ID '" + user.getId() + "' not found");
        }

        validateUser(user);

        Optional<User> existingUserWithLogin = userRepository.findByLogin(user.getLogin());
        if (existingUserWithLogin.isPresent() && !existingUserWithLogin.get().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Login '" + user.getLogin() + "' is already taken by another user");
        }

        Optional<User> existingUserWithEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Email '" + user.getEmail() + "' is already taken by another user");
        }

        userRepository.update(user);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    private boolean verify(String login, String password) {
        return passwordHash.verify(password.toCharArray(), findByLogin(login).get().getPassword());
    }

    public void updatePassword(User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        userRepository.update(user);
    }

    public void updateAvatar(UUID id, InputStream avatar) {
        if (id == null || avatar == null) {
            throw new IllegalArgumentException("User id and avatar stream must not be null");
        }
        userRepository.findById(id).ifPresent(user -> {
            try {
                String oldPath = user.getAvatarPath();
                if (oldPath != null && !oldPath.isBlank()) {
                    try {
                        Path oldFile = Paths.get(oldPath);
                        Files.deleteIfExists(oldFile);
                    } catch (IOException e) {
                    }
                }
                String fileName = user.getLogin() + "_" + UUID.randomUUID() + ".png"; // simple naming
                Path target = Paths.get(avatarDirectory, fileName);
                Files.createDirectories(target.getParent());
                Files.write(target, avatar.readAllBytes());
                user.setAvatarPath(target.toString());
                userRepository.update(user);
            } catch (IOException e) {
                throw new IllegalStateException("Error saving avatar for user ID: " + id, e);
            }
        });
    }

    public void deleteAvatar(UUID id) {
        if (id == null) {
            return;
        }
        userRepository.findById(id).ifPresent(user -> {
            String pathStr = user.getAvatarPath();
            if (pathStr == null || pathStr.isBlank()) {
                return;
            }
            Path p = Paths.get(pathStr);
            try {
                Files.deleteIfExists(p);
            } catch (IOException e) {
                throw new IllegalStateException("Error deleting avatar for user ID: " + id, e);
            }
            user.setAvatarPath("");
            userRepository.update(user);
        });
    }

    public byte[] getAvatar(UUID id) {
        if (id == null) {
            return new byte[0];
        }
        return userRepository.findById(id).map(user -> {
            String pathStr = user.getAvatarPath();
            if (pathStr == null || pathStr.isBlank()) {
                return new byte[0];
            }
            Path p = Paths.get(pathStr);
            if (!Files.exists(p)) {
                return new byte[0];
            }
            try {
                return Files.readAllBytes(p);
            } catch (IOException e) {
                throw new IllegalStateException("Error reading avatar for user ID: " + id, e);
            }
        }).orElse(new byte[0]);
    }

    private static void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (user.getLogin() == null || user.getLogin().trim().isEmpty()) {
            throw new IllegalArgumentException("User login cannot be null or empty");
        }

        if (user.getLogin().trim().length() < 3) {
            throw new IllegalArgumentException("User login must be at least 3 characters long");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty");
        }

        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (user.getBirthDate() != null && user.getBirthDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }
    }

    private static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
}
