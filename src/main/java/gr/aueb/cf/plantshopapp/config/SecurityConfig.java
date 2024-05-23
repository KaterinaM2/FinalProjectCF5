package gr.aueb.cf.plantshopapp.config;

import gr.aueb.cf.plantshopapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Security configuration class for the application.
 * This class configures authentication and authorization settings.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    /**
     * Configures the security filter chain.
     * This method sets up the HTTP security, including CSRF protection and authorization rules.
     *
     * @param http the {@link HttpSecurity} to modify
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF protection
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register").permitAll()  // Allow all users to access the registration endpoint
                        .anyRequest().authenticated())  // Require authentication for all other endpoints
                .formLogin(withDefaults())  // Enable form-based login
                .httpBasic(withDefaults());  // Enable HTTP Basic authentication

        return http.build();
    }

    /**
     * Configures the authentication manager.
     * This method sets up the {@link AuthenticationManager} with a {@link UserDetailsService} and a {@link PasswordEncoder}.
     *
     * @param http the {@link HttpSecurity} to modify
     * @return the configured {@link AuthenticationManager}
     * @throws Exception if an error occurs
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userService)  // Use the userService to load user details
                .passwordEncoder(passwordEncoder());  // Use BCryptPasswordEncoder for password encoding
        return authenticationManagerBuilder.build();
    }

    /**
     * Creates a bean for password encoding.
     * This method returns an instance of {@link BCryptPasswordEncoder}.
     *
     * @return a {@link PasswordEncoder} bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

