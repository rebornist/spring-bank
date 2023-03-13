package shop.mtcoding.bank.config.duumy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;
import shop.mtcoding.bank.domain.user.UserRepository;

@Configuration
public class DummyDevInit {

    @Profile("dev")
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");

        return args -> {
            User ssar = userRepository.save(User.builder()
                    .username("ssar")
                    .password(encPassword)
                    .email("ssar@test.com")
                    .fullname("쌀")
                    .role(UserEnum.CUSTOMER)
                    .build());
        };
    }

}
