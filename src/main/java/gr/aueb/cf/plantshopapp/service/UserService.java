package gr.aueb.cf.plantshopapp.service;


import gr.aueb.cf.plantshopapp.dao.UserRepository;
import gr.aueb.cf.plantshopapp.dto.User;
import gr.aueb.cf.plantshopapp.service.exception.UserAlreadyExistsException;
import gr.aueb.cf.plantshopapp.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new user in the system.
     * @param user User details
     * @return Registered user
     * @throws UserAlreadyExistsException if a user with the same username or email already exists
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
     * Finds a user by username.
     * @param username Username
     * @return User
     * @throws UserNotFoundException if no user is found with the given username
     */
    public User findByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    /**
     * Loads a user by username.
     * This method is required by UserDetailsService.
     * @param username Username
     * @return UserDetails
     * @throws UsernameNotFoundException if no user is found with the given username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER") // You can customize roles as needed
                .build();
    }
}
