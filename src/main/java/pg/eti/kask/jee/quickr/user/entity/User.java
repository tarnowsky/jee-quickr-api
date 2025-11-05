package pg.eti.kask.jee.quickr.user.entity;

import java.time.LocalDate;
import java.util.UUID;

public class User {
    private UUID id;
    private String email;
    private String login;
    private Role role;
    private LocalDate birthdate;
}
