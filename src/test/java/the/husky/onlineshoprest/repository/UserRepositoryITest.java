package the.husky.onlineshoprest.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import the.husky.onlineshoprest.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
@DisplayName("UserRepository Integration Tests")
class UserRepositoryITest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.save(createFirstUserEntity());
        userRepository.save(createSecondUserEntity());
        userRepository.save(createThirdUserEntity());
    }

    @DisplayName("Test: find all user-entities;")
    @Test
    public void testUserRepositoryFindAllReturnsExpectedUsers() {
        List<UserEntity> users = userRepository.findAll();

        int expectedSize = 3;
        UserEntity expectedFirstUser = createFirstUserEntity();
        UserEntity expectedSecondUser = createSecondUserEntity();
        UserEntity expectedThirdUser = createThirdUserEntity();

        int actualSize = users.size();
        UserEntity actualFirstUser = users.get(0);
        UserEntity actualSecondUser = users.get(1);
        UserEntity actualThirdUser = users.get(2);

        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(expectedSize, actualSize);

        assertEquals(expectedFirstUser, actualFirstUser);
        assertEquals(expectedSecondUser, actualSecondUser);
        assertEquals(expectedThirdUser, actualThirdUser);
    }

    @DisplayName("Test: save new user-entity;")
    @Test
    public void testAppendNewUserCheckUserStorage() {
        List<UserEntity> users = userRepository.findAll();

        int expectedSizeBeforeAdded = 3;
        int actualSizeBeforeAdded = users.size();

        UserEntity expectedNewUser = createFourthUserEntity();
        UserEntity actualNewUser = userRepository.save(createFourthUserEntity());

        int expectedSizeAfterAdded = 4;
        int actualSizeAfterAdded = userRepository.findAll().size();

        assertEquals(expectedSizeBeforeAdded, actualSizeBeforeAdded);
        assertEquals(expectedNewUser, actualNewUser);
        assertEquals(expectedSizeAfterAdded, actualSizeAfterAdded);
    }

    @DisplayName("Test: find user-entity by id;")
    @Test
    public void testFindUserByIdCheckExpectedAndActualObject() {
        UserEntity expectedFirstUser = createFirstUserEntity();
        UserEntity expectedSecondUser = createSecondUserEntity();
        UserEntity expectedThirdUser = createThirdUserEntity();

        UserEntity actualFirstUser = userRepository.findById(1L).orElse(null);
        UserEntity actualSecondUser = userRepository.findById(2L).orElse(null);
        UserEntity actualThirdUser = userRepository.findById(3L).orElse(null);

        assert actualFirstUser != null;
        assertNotNull(actualFirstUser);
        assert actualSecondUser != null;
        assertNotNull(actualSecondUser);
        assert actualThirdUser != null;
        assertNotNull(actualThirdUser);

        assertEquals(expectedFirstUser, actualFirstUser);
        assertEquals(expectedSecondUser, actualSecondUser);
        assertEquals(expectedThirdUser, actualThirdUser);
    }

    @DisplayName("Test: delete user-entity by id;")
    @Test
    public void testDeleteUserCheckSizeOfStorageAfterDelete() {
        int expectedSizeBeforeDelete = 3;
        int actualSizeBeforeDelete = userRepository.findAll().size();
        assertEquals(expectedSizeBeforeDelete, actualSizeBeforeDelete);

        userRepository.deleteById(1L);
        int expectedSizeAfterDelete = 2;
        int actualSizeAfterDelete = userRepository.findAll().size();
        assertEquals(expectedSizeAfterDelete, actualSizeAfterDelete);

        userRepository.deleteById(2L);
        int expectedSizeAfterDelete2 = 1;
        int actualSizeAfterDelete2 = userRepository.findAll().size();
        assertEquals(expectedSizeAfterDelete2, actualSizeAfterDelete2);

        userRepository.deleteById(3L);
        int expectedSizeAfterDelete3 = 0;
        int actualSizeAfterDelete3 = userRepository.findAll().size();
        assertEquals(expectedSizeAfterDelete3, actualSizeAfterDelete3);

        List<UserEntity> users = userRepository.findAll();
        assertTrue(users.isEmpty());
    }

    @DisplayName("Test: delete all user-entities;")
    @Test
    public void testDeleteAllUsersFromDB() {
        int expectedSize = 3;
        int actualSize = userRepository.findAll().size();
        assertEquals(expectedSize, actualSize);

        userRepository.deleteAll();
        int expectedSizeAfterDelete = 0;
        int actualSizeAfterDelete = userRepository.findAll().size();
        assertEquals(expectedSizeAfterDelete, actualSizeAfterDelete);

        List<UserEntity> users = userRepository.findAll();
        assertTrue(users.isEmpty());
    }

    @DisplayName("Test: find user-entity by login;")
    @Test
    public void testFindUserByLogin() {
        UserEntity expectedUser = createFirstUserEntity();
        UserEntity actualUser = userRepository.findByLogin("voldemort").orElse(null);

        assert actualUser != null;
        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @DisplayName("Test: find user-entity by email;")
    @Test
    public void testFindUserByEmail() {
        UserEntity expectedUser = createFirstUserEntity();
        UserEntity actualUser = userRepository.findByEmail("dark_lord@gmail.com").orElse(null);

        assert actualUser != null;
        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @DisplayName("Test: find all user-entities by name;")
    @Test
    public void testFindAllUsersByName() {
        List<UserEntity> expectedUsers = List.of(createSecondUserEntity());
        List<UserEntity> actualUsers = userRepository.findAllByName("Harry Potter").orElse(null);

        assert actualUsers != null;
        assertNotNull(actualUsers);
        assertEquals(expectedUsers, actualUsers);
    }

    @DisplayName("Test: find all user-entities by age;")
    @Test
    public void testFindAllUsersByAge() {
        List<UserEntity> expectedUsers = List.of(createSecondUserEntity(), createThirdUserEntity());
        List<UserEntity> actualUsers = userRepository.findAllByAge(17).orElse(null);
        assert actualUsers != null;

        int expectedSize = 2;
        int actualSize = actualUsers.size();

        assertNotNull(actualUsers);
        assertEquals(expectedUsers, actualUsers);
        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("Test: try to save user-entity with duplicate login, throw exception;")
    @Test
    public void testSaveUserWithDuplicateLoginThrowException() {
        UserEntity userWithDuplicateLogin = createUserWithDuplicateLogin();

        Throwable thrown = assertThrows(Exception.class, () -> userRepository.save(userWithDuplicateLogin));
        assertNotNull(thrown.getMessage());
    }

    @DisplayName("Test: try to save user-entity with duplicate email, throw exception;")
    @Test
    public void testSaveUserWithDuplicateEmailThrowException() {
        UserEntity userWithDuplicateEmail = createUserWithDuplicateEmail();

        Throwable thrown = assertThrows(Exception.class, () -> userRepository.save(userWithDuplicateEmail));
        assertNotNull(thrown.getMessage());
    }


    private UserEntity createFirstUserEntity() {
        return UserEntity.builder()
                .userId(1L)
                .name("Tom Riddle")
                .login("voldemort")
                .password("lord")
                .email("dark_lord@gmail.com")
                .age(100)
                .registrationDate(LocalDate.now())
                .build();
    }

    private UserEntity createSecondUserEntity() {
        return UserEntity.builder()
                .userId(2L)
                .name("Harry Potter")
                .login("chosen_one")
                .password("boy_who_survived")
                .email("chosen_one@gmail.com")
                .age(17)
                .registrationDate(LocalDate.now())
                .build();
    }

    private UserEntity createThirdUserEntity() {
        return UserEntity.builder()
                .userId(3L)
                .name("Ron Weasley")
                .login("ronald")
                .password("ron")
                .email("redhead@gmail.com")
                .age(17)
                .registrationDate(LocalDate.now())
                .build();
    }

    private UserEntity createFourthUserEntity() {
        return UserEntity.builder()
                .userId(4L)
                .name("Hermione Granger")
                .login("hermione")
                .password("gin")
                .email("witch@gmail.com")
                .age(17)
                .registrationDate(LocalDate.now())
                .build();
    }

    private UserEntity createUserWithDuplicateLogin() {
        return UserEntity.builder()
                .userId(5L)
                .name("Draco Malfoy")
                .login("chosen_one")
                .password("malfoy")
                .email("draco@gmail.com")
                .age(17)
                .registrationDate(LocalDate.now())
                .build();
    }

    private UserEntity createUserWithDuplicateEmail() {
        return UserEntity.builder()
                .userId(5L)
                .name("Draco Malfoy")
                .login("draco")
                .password("malfoy")
                .email("chosen_one@gmail.com")
                .age(17)
                .registrationDate(LocalDate.now())
                .build();
    }
}
