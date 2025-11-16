package pg.eti.kask.jee.quickr.user.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Stateless
@LocalBean
@NoArgsConstructor(force = true)
public class UserAvatarService {
    private final Path avatarDirectory;

    @Inject
    public UserAvatarService(@Named("avatarDirectory") String avatarDirectory) {
        this.avatarDirectory = Paths.get(avatarDirectory);
        try {
            Files.createDirectories(this.avatarDirectory);
        } catch (IOException e) {
            throw new RuntimeException("Cannot create avatar directory: " + avatarDirectory, e);
        }
    }

    public void saveAvatar(UUID userId, InputStream avatarStream) {
        Path avatarPath = getAvatarFilePath(userId);
        try {
            Files.copy(avatarStream, avatarPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save avatar for user: " + userId, e);
        }
    }

    public byte[] getAvatar(UUID userId) {
        Path avatarPath = getAvatarFilePath(userId);

        if (!Files.exists(avatarPath)) {
            throw new NotFoundException("Avatar not found for user: " + userId);
        }

        try {
            return Files.readAllBytes(avatarPath);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read avatar for user: " + userId, e);
        }
    }

    public void deleteAvatar(UUID userId) {
        Path avatarPath = getAvatarFilePath(userId);
        try {
            Files.deleteIfExists(avatarPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete avatar for user: " + userId, e);
        }
    }

    public String getAvatarPath(UUID userId) {
        return getAvatarFilePath(userId).toString();
    }

    private Path getAvatarFilePath(UUID userId) {
        return avatarDirectory.resolve(userId.toString() + ".png");
    }
}
