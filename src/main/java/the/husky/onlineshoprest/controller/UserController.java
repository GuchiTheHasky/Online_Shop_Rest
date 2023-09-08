package the.husky.onlineshoprest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import the.husky.onlineshoprest.entity.UserEntity;
import the.husky.onlineshoprest.exception.user.UserAlreadyExistException;
import the.husky.onlineshoprest.model.User;
import the.husky.onlineshoprest.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting all users: %s", e.getMessage());
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserEntity userEntity) {
        try {
            User user = userService.appendUser(userEntity);
            return ResponseEntity.ok(user);
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/find/by/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting user by id: %s", id);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @GetMapping("/find/by/login/{login}")
    public ResponseEntity<?> getUsersByLogin(@PathVariable("login") String login) {
        try {
            User user = userService.getUserByLogin(login);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting user by login: %s", login);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @GetMapping("/find/by/email/{email}")
    public ResponseEntity<?> getUsersByEmail(@PathVariable("email") String email) {
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting user by email: %s", email);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @GetMapping("/filter/by/name/{name}")
    public ResponseEntity<?> getUsersByName(@PathVariable("name") String name) {
        try {
            List<User> users = userService.getAllUsersByName(name);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting users by name: %s", name);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @GetMapping("/filter/by/age/{age}")
    public ResponseEntity<?> getUsersByAge(@PathVariable("age") int age) {
        try {
            List<User> users = userService.getAllUsersByAge(age);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            String errorMessage = String.format("Error during getting users by age: %s", age);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @PostMapping("/edit/{userId}")
    public ResponseEntity<?> editUser(@PathVariable("userId") long id, @RequestBody UserEntity userEntity) {
        try {
            User user = userService.editUser(id, userEntity);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            String errorMessage = String.format("Error during editing user by id: %s", id);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        try {
            User user = userService.deleteUser(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            String errorMessage = String.format("Error during deleting user by id: %s", id);
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAllUsers() {
        try {
            userService.deleteAllUsers();
            return ResponseEntity.ok("All users were deleted");
        } catch (Exception e) {
            String errorMessage = String.format("Error during deleting all users: %s", e.getMessage());
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }
}
