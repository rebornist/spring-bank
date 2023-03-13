package shop.mtcoding.bank.serivce;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountSaveReqDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountSaveRespDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;

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

	@Setter
	@Getter
	public static class AccountListRespDto {
		private String fullname;
		private List<AccountDto> accounts = new ArrayList<>();

		public AccountListRespDto(User user, List<Account> accounts) {
			this.fullname = user.getFullname();
			// this.accounts = accounts.stream().map(account -> new
			// AccountDto(account)).collect(Collectors.toList());
			this.accounts = accounts.stream().map(AccountDto::new).collect(Collectors.toList());

		}

		@Setter
		@Getter
		private class AccountDto {
			private Long id;
			private Long number;
			private Long balance;

			public AccountDto(Account account) {
				this.id = account.getId();
				this.number = account.getNumber();
				this.balance = account.getBalance();
			}
		}
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

}
