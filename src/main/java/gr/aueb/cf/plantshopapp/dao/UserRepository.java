package gr.aueb.cf.plantshopapp.dao;

import gr.aueb.cf.plantshopapp.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
