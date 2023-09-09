package the.husky.onlineshoprest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import the.husky.onlineshoprest.entity.UserEntity;
import the.husky.onlineshoprest.exception.user.UserAlreadyExistException;
import the.husky.onlineshoprest.exception.user.UserException;
import the.husky.onlineshoprest.exception.user.UserNotFoundException;
import the.husky.onlineshoprest.model.User;
import the.husky.onlineshoprest.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(User::toModel)
                .toList();
    }

    public User getUserById(Long id) { // +
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if (userEntity == null) {
            String errorMessage = String.format("User with id: %s is not exist", id);
            log.error("Error during getting user by id: {}", errorMessage);
            throw new UserNotFoundException(errorMessage);
        }
        return User.toModel(userEntity);
    }

    public User appendUser(UserEntity userEntity) { // +
        if (isMandatoryFieldsAlreadyTaken(userEntity)) {
            String errorMessage = String.format("User with current login: %s || email: %s is already exist",
                    userEntity.getLogin(), userEntity.getEmail());
            log.error("Error during adding user: {}", errorMessage);
            throw new UserAlreadyExistException(errorMessage);
        }
        UserEntity user = userRepository.save(userEntity);
        return User.toModel(user);
    }

    public User editUser(long id, UserEntity userEntity) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserEntity currentUser = userOptional.get();
            currentUser.setName(userEntity.getName());
            currentUser.setLogin(userEntity.getLogin());
            currentUser.setPassword(userEntity.getPassword());
            currentUser.setEmail(userEntity.getEmail());
            currentUser.setAge(userEntity.getAge());
            userRepository.save(currentUser);
            return User.toModel(currentUser);
        }
        String errorMessage = String.format("User with id: %s is not exist", id);
        log.error("Error during editing user: {}", errorMessage);
        throw new UserNotFoundException(errorMessage);
    }

    public void deleteUser(long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            UserEntity currentUser = userOptional.get();
            userRepository.delete(currentUser);
        }
        String errorMessage = String.format("User with id: %s is not exist", id);
        log.error("Error during deleting user: {}", errorMessage);
        throw new UserNotFoundException(errorMessage);
    }

    public void deleteAllUsers() {
        try {
            userRepository.deleteAll();
        } catch (Exception e) {
            String errorMessage = "Error during deleting all users";
            log.error("Error during deleting all users: {}", errorMessage);
            throw new UserException(errorMessage);
        }
    }

    public User getUserByLogin(String login) {
        Optional<UserEntity> userOptional = userRepository.findByLogin(login);
        if (userOptional.isPresent()) {
            UserEntity currentUser = userOptional.get();
            return User.toModel(currentUser);
        }
        String errorMessage = String.format("User with login: %s is not exist", login);
        log.error("Error during getting user by login: {}", errorMessage);
        throw new UserNotFoundException(errorMessage);
    }

    public User getUserByEmail(String email) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserEntity currentUser = userOptional.get();
            return User.toModel(currentUser);
        }
        String errorMessage = String.format("User with email: %s is not exist", email);
        log.error("Error during getting user by email: {}", errorMessage);
        throw new UserNotFoundException(errorMessage);
    }

    public List<User> getAllUsersByName(String name) {
        Optional<List<UserEntity>> userOptional = userRepository.findAllByName(name);
        if (userOptional.isPresent()) {
            List<UserEntity> currentUser = userOptional.get();
            return currentUser.stream()
                    .map(User::toModel)
                    .toList();
        }
        String errorMessage = String.format("User with name: %s is not exist", name);
        log.error("Error during getting user by name: {}", errorMessage);
        throw new UserNotFoundException(errorMessage);
    }

    public List<User> getAllUsersByAge(int age) {
        Optional<List<UserEntity>> userOptional = userRepository.findAllByAge(age);
        if (userOptional.isPresent()) {
            List<UserEntity> currentUser = userOptional.get();
            return currentUser.stream()
                    .map(User::toModel)
                    .toList();
        }
        String errorMessage = String.format("User with age: %s is not exist", age);
        log.error("Error during getting user by age: {}", errorMessage);
        throw new UserNotFoundException(errorMessage);
    }

    private boolean isMandatoryFieldsAlreadyTaken(UserEntity userEntity) {
        return userRepository.findByLogin(userEntity.getLogin()).isPresent() ||
                userRepository.findByEmail(userEntity.getEmail()).isPresent();
    }
}
