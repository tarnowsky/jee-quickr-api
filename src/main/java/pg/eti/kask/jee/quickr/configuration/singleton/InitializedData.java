package pg.eti.kask.jee.quickr.configuration.singleton;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import pg.eti.kask.jee.quickr.entity.Order;
import pg.eti.kask.jee.quickr.entity.User;
import pg.eti.kask.jee.quickr.entity.Venue;
import pg.eti.kask.jee.quickr.entity.enums.UserRoles;
import pg.eti.kask.jee.quickr.entity.enums.VenueCategory;
import pg.eti.kask.jee.quickr.repository.api.OrderRepository;
import pg.eti.kask.jee.quickr.repository.api.UserRepository;
import pg.eti.kask.jee.quickr.repository.api.VenueRepository;

import java.time.LocalDate;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
@DependsOn("InitializeAdminService")
@DeclareRoles({ UserRoles.ADMIN, UserRoles.USER })
@RunAs(UserRoles.ADMIN)
@Log
public class InitializedData {

        private final UserRepository userRepository;
        private final OrderRepository orderRepository;
        private final VenueRepository venueRepository;
        private final Pbkdf2PasswordHash passwordHash;

        @Inject
        public InitializedData(
                        UserRepository userRepository,
                        OrderRepository orderRepository,
                        VenueRepository venueRepository,
                        @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash) {
                this.userRepository = userRepository;
                this.orderRepository = orderRepository;
                this.venueRepository = venueRepository;
                this.passwordHash = passwordHash;
        }

        @PostConstruct
        @SneakyThrows
        public void init() {
                if (!userRepository.existsByLogin("admin")) {

                        // -=-=-=-=-=-=-=-= USERS =-=-=-=-=-=-=-=-=-

                        User admin = User.builder()
                                        .id(UUID.fromString("99426767-3f31-4796-bed6-316fed80dcc0"))
                                        .email("admin@quickr.com")
                                        .login("admin")
                                        .password(passwordHash.generate("adminadmin".toCharArray()))
                                        .role(UserRoles.ADMIN)
                                        .role(UserRoles.USER)
                                        .birthDate(LocalDate.of(1969, 5, 17))
                                        .build();

                        User victor = User.builder()
                                        .id(UUID.fromString("76d2704d-1531-404a-94de-ccc38030bb9f"))
                                        .email("victor@quickr.com")
                                        .login("victor")
                                        .password(passwordHash.generate("useruser".toCharArray()))
                                        .role(UserRoles.USER)
                                        .birthDate(LocalDate.of(2002, 7, 18))
                                        .build();

                        User mike = User.builder()
                                        .id(UUID.fromString("25ba2834-f92d-4f77-98c3-46355037c624"))
                                        .email("mike@quickr.com")
                                        .login("mike")
                                        .password(passwordHash.generate("useruser".toCharArray()))
                                        .role(UserRoles.USER)
                                        .birthDate(LocalDate.of(2003, 1, 6))
                                        .build();

                        User debbie = User.builder()
                                        .id(UUID.fromString("7d81de18-a1ff-4b9b-959c-0bf8035d66c7"))
                                        .email("debbie@quickr.com")
                                        .login("debbie")
                                        .password(passwordHash.generate("useruser".toCharArray()))
                                        .role(UserRoles.USER)
                                        .birthDate(LocalDate.of(2003, 12, 20))
                                        .build();

                        userRepository.create(admin);
                        userRepository.create(victor);
                        userRepository.create(mike);
                        userRepository.create(debbie);

                        // -=-=-=-=-=-=-=-= VENUES =-=-=-=-=-=-=-=-=-

                        Venue foodTrack = Venue.builder()
                                        .id(UUID.fromString("2ff1b71b-6927-4446-b4e0-1c573b38bc70"))
                                        .name("Burgers on Wheels")
                                        .venueCategory(VenueCategory.FOOD_TRUCK)
                                        .user(victor)
                                        .capacity(4)
                                        .build();

                        Venue restaurant = Venue.builder()
                                        .id(UUID.fromString("d59593c0-0b25-48d4-b3fa-5451dd9a4c32"))
                                        .name("Fancy Shmency")
                                        .venueCategory(VenueCategory.RESTAURANT)
                                        .user(victor)
                                        .capacity(150)
                                        .build();

                        Venue fastFood = Venue.builder()
                                        .id(UUID.fromString("47c488b9-aa07-4432-b3c3-a6556a487a47"))
                                        .name("McDuck")
                                        .venueCategory(VenueCategory.FAST_FOOD)
                                        .user(mike)
                                        .capacity(100)
                                        .build();

                        Venue cafe = Venue.builder()
                                        .id(UUID.fromString("46e3baf5-841d-4d15-9fc1-460fc1547cd3"))
                                        .name("Artists and Others")
                                        .venueCategory(VenueCategory.CAFE)
                                        .user(debbie)
                                        .capacity(30)
                                        .build();

                        venueRepository.create(foodTrack);
                        venueRepository.create(restaurant);
                        venueRepository.create(fastFood);
                        venueRepository.create(cafe);

                        // -=-=-=-=-=-=-=-= ORDERS =-=-=-=-=-=-=-=-=-

                        Order burgerAndCoke = Order.builder()
                                        .id(UUID.fromString("bb31dcb1-6b55-47ec-a06c-be7d5ad76372"))
                                        .price(4.99)
                                        .orderDate(LocalDate.of(2025, 11, 2))
                                        .user(mike)
                                        .venue(fastFood)
                                        .build();

                        Order steak = Order.builder()
                                        .id(UUID.fromString("4141c59a-e436-4322-8336-db8facce08c0"))
                                        .price(119.99)
                                        .orderDate(LocalDate.of(2025, 11, 3))
                                        .user(victor)
                                        .venue(restaurant)
                                        .build();

                        Order cafeConLatte = Order.builder()
                                        .id(UUID.fromString("0a4bd66b-4e05-4f37-a022-b7a3ac30f3e8"))
                                        .price(2.99)
                                        .orderDate(LocalDate.of(2025, 10, 2))
                                        .user(debbie)
                                        .venue(cafe)
                                        .build();

                        Order burgerNoCoke = Order.builder()
                                        .id(UUID.fromString("48efc52a-7260-4254-8966-63d7338ca694"))
                                        .price(3.99)
                                        .orderDate(LocalDate.of(2025, 9, 28))
                                        .venue(foodTrack)
                                        .user(victor)
                                        .build();

                        Order cappuccino = Order.builder()
                                        .id(UUID.fromString("8b44c1c8-b1b0-4203-9a64-f5b78129149c"))
                                        .price(3.29)
                                        .orderDate(LocalDate.of(2025, 8, 6))
                                        .user(mike)
                                        .venue(cafe)
                                        .build();

                        orderRepository.create(burgerNoCoke);
                        orderRepository.create(steak);
                        orderRepository.create(burgerAndCoke);
                        orderRepository.create(cafeConLatte);
                        orderRepository.create(cappuccino);
                }
        }
}
