package gr.aueb.cf.plantshopapp.controller;

import gr.aueb.cf.plantshopapp.dto.User;
import gr.aueb.cf.plantshopapp.service.exception.UserAlreadyExistsException;
import gr.aueb.cf.plantshopapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling user-related requests
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Registers a new user
     * @param user User details to register
     * @return  the registered user
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Logs in a user
     *
     * @param user the user credentials
     * @return the logged-in user
     */
    @PostMapping("/login")
    public User loginUser(@RequestBody User user) {
        return userService.loginUser(user);
    }

    /**
     * Retrieves all users
     *
     * @return a list of users
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();

    }
}

