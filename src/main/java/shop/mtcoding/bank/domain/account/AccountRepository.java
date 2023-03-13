package shop.mtcoding.bank.domain.account;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

	// checkpoint : 계좌 소유자 확인 시에 쿼리가 두번 나가기 때문에 Join fetch를 사용하여 한번에 가져오도록 수정
	Optional<Account> findByNumber(@NotNull Long number);

	// jpa 메서드
	// select * from account where user_id = ?
	List<Account> findByUser_Id(@NotNull Long userId);
};
