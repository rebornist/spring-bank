package shop.mtcoding.bank.web;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountSaveReqDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountListRespDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountSaveRespDto;
import shop.mtcoding.bank.serivce.AccountService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/s/account")
    public ResponseEntity<?> createAccount(@RequestBody @Valid AccountSaveReqDto accountSaveReqDto,
            BindingResult bindingResult, @AuthenticationPrincipal LoginUser loginUser) {
        AccountSaveRespDto accountSaveRespDto = accountService.createAccount(accountSaveReqDto,
                loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌가 생성되었습니다.", accountSaveRespDto), HttpStatus.CREATED);
    }

    // 인증에 필요하고, account 테이블에 1번 row를 가져오는 것이므로, getMapping으로 설정
    // 권한처리 때문에 선호하지 않는다.

    // 인증이 필요하고, account 테이블에 login한 유저의 계좌만 검색
    @GetMapping("/s/account/login-user")
    public ResponseEntity<?> findUserAccount (@AuthenticationPrincipal LoginUser loginUser) {
        AccountListRespDto accountListRespDto = accountService.searchAccountByUser(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌가 조회되었습니다.", accountListRespDto), HttpStatus.OK);
    }

    @DeleteMapping("/s/account/{accountNumber}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long accountNumber, @AuthenticationPrincipal LoginUser loginUser) {
        accountService.deleteAccount(accountNumber, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌가 삭제되었습니다.", null), HttpStatus.OK);
    }

}
