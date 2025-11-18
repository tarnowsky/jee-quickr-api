package pg.eti.kask.jee.quickr.configuration.singleton;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import pg.eti.kask.jee.quickr.entity.User;
import pg.eti.kask.jee.quickr.entity.enums.UserRoles;
import pg.eti.kask.jee.quickr.repository.api.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {
    private final UserRepository userRepository;

    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public InitializeAdminService(
            UserRepository userRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (userRepository.findByLogin("admin-service").isEmpty()) {

            User admin = User.builder()
                    .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                    .login("admin-service")
                    .birthDate(LocalDate.of(1990, 10, 21))
                    .email("admin-service@quickr.com")
                    .password(passwordHash.generate("adminadmin".toCharArray()))
                    .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                    .build();

            userRepository.create(admin);
        }
    }
}
