package pl.zielinski.shop.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zielinski.shop.security.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);


    Optional<User> findByUsername(String username);
}
