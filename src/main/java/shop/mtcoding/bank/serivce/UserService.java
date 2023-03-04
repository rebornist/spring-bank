package shop.mtcoding.bank.serivce;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.user.UserReqDto.JoinReqDto;
import shop.mtcoding.bank.dto.user.UserRespDto.JoinRespDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;
import shop.mtcoding.bank.domain.user.User;

@RequiredArgsConstructor
@Service
public class UserService {
    
    // private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 서비스는 DTO를 받아서 처리하고, DTO를 반환한다.
    @Transactional // 트랜잭션 처리를 위한 어노테이션
    public JoinRespDto signup(JoinReqDto joinRepDto) {
        // 1. 동일 유저네임 존재 검사
        Optional<User> userOP = userRepository.findByUsername(joinRepDto.getUsername());
        // 동일 유저네임이 존재하면 예외 발생
        if (userOP.isPresent()) {
            // 예외 발생
            throw new CustomApiException("이미 존재하는 유저네임입니다.");
        }

        // 2. 패스워드 인코딩
        User userPS = userRepository.save(joinRepDto.toEntity(passwordEncoder));


        // 3. dto 응답
        return new JoinRespDto(userPS);
    }

}
