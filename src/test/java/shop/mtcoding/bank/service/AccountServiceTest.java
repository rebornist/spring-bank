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

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.bank.config.duumy.DummyObject;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountSaveReqDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountSaveRespDto;
import shop.mtcoding.bank.serivce.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest extends DummyObject {

    @InjectMocks // @Mock이 붙은 객체를 주입
    private AccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Spy // 진짜 객체를 InjectMocks에 주입
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void createAccountTest() throws Exception {

        // given
        Long userId = 1L;

        AccountSaveReqDto accountSaveReqDto = new AccountSaveReqDto();
        accountSaveReqDto.setNumber(1111L);
        accountSaveReqDto.setPassword(1234L);

        // stub 1
        User ssar = newMockUser(userId, "ssar", "쌀");
        when(userRepository.findById(any())).thenReturn(Optional.of(ssar));

        // stub 2
        when(accountRepository.findByNumber(any())).thenReturn(Optional.empty());

        // stub 3
        Account saarAccount = newMockAccount(1L, 1111L, 1000L, ssar);
        when(accountRepository.save(any())).thenReturn(saarAccount);

        // when
        AccountSaveRespDto accountSaveRespDto = accountService.createAccount(accountSaveReqDto, userId);
        String responseBody = objectMapper.writeValueAsString(accountSaveRespDto);
        System.out.println(responseBody);

        // then
        assertThat(accountSaveReqDto.getNumber()).isEqualTo(1111L);
    }

}
