package the.husky.onlineshoprest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
       List<UserEntity> userEntities = userRepository.findAll();
       return userEntities.stream()
                .map(User::toModel)
                .toList();
    }

    public User getUserById(Long id) {
        try {
            UserEntity userEntity = userRepository.findById(id).orElse(null);
            assert userEntity != null;
            return User.toModel(userEntity);
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting user by id: %s", id);
            throw new UserException(errorMessage, e.getCause());
        }
    }

    public User appendUser(UserEntity userEntity) {
        try {
           UserEntity user = userRepository.save(userEntity);
           return User.toModel(user);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = String.format("Current login is already exist: %s", userEntity.getLogin());
            throw new UserAlreadyExistException(errorMessage);
        } catch (Exception e) {
            String errorMessage = String.format("Error during adding user: %s", userEntity.getLogin());
            throw new UserException(errorMessage, e.getCause());
        }
    }

    public User editUser(long id, UserEntity userEntity) {
        try {
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
            } else {
                String errorMessage = String.format("User with id: %s is not exist", id);
                throw new UserNotFoundException(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = String.format("Error during editing user: %s", userEntity.getLogin());
            throw new UserException(errorMessage, e.getCause());
        }
    }

    public User deleteUser(long id) {
        try {
            Optional<UserEntity> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                UserEntity currentUser = userOptional.get();
                userRepository.delete(currentUser);
                return User.toModel(currentUser);
            } else {
                String errorMessage = String.format("User with id: %s is not exist", id);
                throw new UserNotFoundException(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = String.format("Error during deleting user with id: %s", id);
            throw new UserException(errorMessage, e.getCause());
        }
    }

    public void deleteAllUsers() {
        try {
            userRepository.deleteAll();
        } catch (Exception e) {
            String errorMessage = "Error during deleting all users";
            throw new UserException(errorMessage, e.getCause());
        }
    }

    public User getUserByLogin(String login) {
        try {
            Optional<UserEntity> userOptional = userRepository.findByLogin(login);
            if (userOptional.isPresent()) {
                UserEntity currentUser = userOptional.get();
                return User.toModel(currentUser);
            } else {
                String errorMessage = String.format("User with login: %s is not exist", login);
                throw new UserNotFoundException(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting user by login: %s", login);
            throw new UserException(errorMessage, e.getCause());
        }
    }

    public User getUserByEmail(String email) {
        try {
            Optional<UserEntity> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                UserEntity currentUser = userOptional.get();
                return User.toModel(currentUser);
            } else {
                String errorMessage = String.format("User with email: %s is not exist", email);
                throw new UserNotFoundException(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting user by email: %s", email);
            throw new UserException(errorMessage, e.getCause());
        }
    }

    public List<User> getAllUsersByName(String name) {
        try {
            Optional<List<UserEntity>> userOptional = userRepository.findAllByName(name);
            if (userOptional.isPresent()) {
                List<UserEntity> currentUser = userOptional.get();
                return currentUser.stream()
                        .map(User::toModel)
                        .toList();
            } else {
                String errorMessage = String.format("User with name: %s is not exist", name);
                throw new UserNotFoundException(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting user by name: %s", name);
            throw new UserException(errorMessage, e.getCause());
        }
    }

    public List<User> getAllUsersByAge(int age) {
        try {
            Optional<List<UserEntity>> userOptional = userRepository.findAllByAge(age);
            if (userOptional.isPresent()) {
                List<UserEntity> currentUser = userOptional.get();
                return currentUser.stream()
                        .map(User::toModel)
                        .toList();
            } else {
                String errorMessage = String.format("User with age: %s is not exist", age);
                throw new UserNotFoundException(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting user by age: %s", age);
            throw new UserException(errorMessage, e.getCause());
        }
    }
}
