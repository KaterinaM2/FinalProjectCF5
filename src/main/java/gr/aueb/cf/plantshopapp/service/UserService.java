package gr.aueb.cf.plantshopapp.service;


import gr.aueb.cf.plantshopapp.dao.UserRepository;
import gr.aueb.cf.plantshopapp.dto.User;
import gr.aueb.cf.plantshopapp.service.exception.UserAlreadyExistsException;
import gr.aueb.cf.plantshopapp.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing users
 * Implements UserDetailsService for Spring Security authentication
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    /**
     * Constructor-based dependency injection for UserRepository
     * @param userRepository the user repository
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Setter-based dependency injection for PasswordEncoder
     * @param passwordEncoder the password encoder
     */
    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user in the system
     * @param user the user to register
     * @return the registered user
     * @throws UserAlreadyExistsException if the user already exists
     */
    public User registerUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with this username or email");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Finds a user by username
     * @param username the username to search
     * @return the found user
     * @throws UserNotFoundException if the user is not found
     */
    public User findByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with the username: " + username));
    }

    /**
     * Loads a user by username for spring security authentication
     * @param username the username to search
     * @return the UserDetails object
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with theusername: " + username));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

    /**
     * Logs in a user
     *
     * @param user the user credentials
     * @return the logged-in user
     */
    public User loginUser(User user) {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent() && passwordEncoder.matches(user.getPassword(), optionalUser.get().getPassword())) {
            return optionalUser.get();
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    /**
     * Retrieves all users
     *
     * @return a list of users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
