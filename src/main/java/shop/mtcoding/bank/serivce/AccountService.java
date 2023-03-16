package shop.mtcoding.bank.serivce;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountSaveReqDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountListRespDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountSaveRespDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;
import shop.mtcoding.bank.handler.ex.CustomForbiddenException;

@RequiredArgsConstructor
@org.springframework.transaction.annotation.Transactional(readOnly = true)
@Service
public class AccountService {

	private final UserRepository userRepository;
	private final AccountRepository accountRepository;

	public AccountListRespDto searchAccountByUser(Long userId) {
		// user가 존재하는지 확인
		User userPS = userRepository.findById(userId)
				.orElseThrow(() -> new CustomApiException("해당 유저가 존재하지 않습니다."));

		List<Account> accountListPS = accountRepository.findByUser_Id(userPS.getId());
		return new AccountListRespDto(userPS, accountListPS);
	}



	@Transactional
	public AccountSaveRespDto createAccount(AccountSaveReqDto accountSaveReqDto, Long userId) {
		// user가 존재하는지 확인
		User userPS = userRepository.findById(userId)
				.orElseThrow(() -> new CustomApiException("해당 유저가 존재하지 않습니다."));

		// 해당 계좌번호가 존재하는지 확인
		Optional<Account> accountOP = accountRepository.findByNumber(accountSaveReqDto.getNumber());
		if (accountOP.isPresent()) {
			throw new CustomApiException("해당 계좌번호가 이미 존재합니다.");
		}

		// 계좌 등록
		Account accountPS = accountRepository.save(accountSaveReqDto.toEntity(userPS));

		// DTO를 반환
		return new AccountSaveRespDto(accountPS);
	}

	@Transactional
	public void deleteAccount(Long accountNumber, Long userId) {
		// account가 존재하는지 확인
		Account accountPS = accountRepository.findById(accountNumber)
				.orElseThrow(() -> new CustomApiException("해당 계좌가 존재하지 않습니다."));

		// accout의 user와 login한 user가 같은지 확인
		accountPS.checkUser(userId);

		// account 삭제
		accountRepository.deleteById(accountPS.getId());
	}

}
