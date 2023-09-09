package the.husky.onlineshoprest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import the.husky.onlineshoprest.entity.UserEntity;
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
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserEntity userEntity) {
        User user = userService.appendUser(userEntity);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/find/by/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/find/by/login/{login}")
    public ResponseEntity<?> getUsersByLogin(@PathVariable("login") String login) {
        User user = userService.getUserByLogin(login);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/find/by/email/{email}")
    public ResponseEntity<?> getUsersByEmail(@PathVariable("email") String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/filter/by/name/{name}")
    public ResponseEntity<?> getUsersByName(@PathVariable("name") String name) {
        List<User> users = userService.getAllUsersByName(name);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/filter/by/age/{age}")
    public ResponseEntity<?> getUsersByAge(@PathVariable("age") int age) {
        List<User> users = userService.getAllUsersByAge(age);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/edit/{userId}")
    public ResponseEntity<?> editUser(@PathVariable("userId") long id, @RequestBody @Valid UserEntity userEntity) {
        User user = userService.editUser(id, userEntity);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with id: " + id + " was deleted.");
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok("All users were deleted.");
    }
}
