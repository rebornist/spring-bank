package shop.mtcoding.bank.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    // select * from user where username = ?;
    public Optional<User> findByUsername(String string); // Jpa Naming 전략
    
}
