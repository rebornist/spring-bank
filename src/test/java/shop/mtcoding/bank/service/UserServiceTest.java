package shop.mtcoding.bank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import shop.mtcoding.bank.config.duumy.DummyObject;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.user.UserReqDto.JoinReqDto;
import shop.mtcoding.bank.dto.user.UserRespDto.JoinRespDto;
import shop.mtcoding.bank.serivce.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends DummyObject {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository UserRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void signup_test() throws Exception {
        // given
        JoinReqDto dto = new JoinReqDto();
        dto.setUsername("ssar");
        dto.setPassword("1234");
        dto.setEmail("ssar@nate.com");
        dto.setFullname("쌀");
        
        // stub1
        when(UserRepository.findByUsername(any())).thenReturn(Optional.empty());

        // stub2
        User ssar = newMockUser(1L, "ssar", "쌀");
        when(UserRepository.save(any())).thenReturn(ssar);

        // when
        JoinRespDto joinRespDto = userService.signup(dto);
        System.out.println("joinRespDto : " + joinRespDto);


        // then
        assertThat(dto.getUsername()).isEqualTo("ssar");

    }
    
}
