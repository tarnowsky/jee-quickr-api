package pg.eti.kask.jee.quickr.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import pg.eti.kask.jee.quickr.user.entity.User;
import pg.eti.kask.jee.quickr.user.entity.UserRole;
import pg.eti.kask.jee.quickr.user.service.UserService;

import java.time.LocalDate;
import java.util.UUID;

@WebListener
public class InitializedData implements ServletContextListener {

    private UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        userService = (UserService) event.getServletContext().getAttribute("userService");
        init();
    }

    @SneakyThrows
    public void init() {
        User admin = User.builder()
                .id(UUID.fromString("99426767-3f31-4796-bed6-316fed80dcc0"))
                .email("admin@quickr.com")
                .login("admin")
                .password("adminadmin")
                .role(UserRole.ADMIN)
                .birthDate(LocalDate.of(1969,5,17))
                .build();

        User victor = User.builder()
                .id(UUID.fromString("76d2704d-1531-404a-94de-ccc38030bb9f"))
                .email("victor@quickr.com")
                .login("victor")
                .password("victorvictor")
                .role(UserRole.ADMIN)
                .birthDate(LocalDate.of(2002,7,18))
                .build();

        User mike = User.builder()
                .id(UUID.fromString("25ba2834-f92d-4f77-98c3-46355037c624"))
                .email("mike@quickr.com")
                .login("mike")
                .password("mikemike")
                .role(UserRole.ADMIN)
                .birthDate(LocalDate.of(2003,1,6))
                .build();

        User debbie = User.builder()
                .id(UUID.fromString("7d81de18-a1ff-4b9b-959c-0bf8035d66c7"))
                .email("debbie@quickr.com")
                .login("debbie")
                .password("debbiedebbie")
                .role(UserRole.ADMIN)
                .birthDate(LocalDate.of(2003,12,20))
                .build();

        userService.create(admin);
        userService.create(victor);
        userService.create(mike);
        userService.create(debbie);
    }

}
