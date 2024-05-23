package gr.aueb.cf.plantshopapp.controller;

import gr.aueb.cf.plantshopapp.dto.User;
import gr.aueb.cf.plantshopapp.service.exception.UserAlreadyExistsException;
import gr.aueb.cf.plantshopapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Registers a new user.
     * @param user User details
     * @return ResponseEntity with the registered user
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

    // Add more endpoints for login, update user etc.
}

