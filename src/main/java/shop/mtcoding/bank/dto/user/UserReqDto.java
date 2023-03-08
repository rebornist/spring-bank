package shop.mtcoding.bank.dto.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

public class UserReqDto {

    @Getter
    @Setter
    public static class LoginReqDto {
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
    }

    @Getter
    @Setter
    public static class JoinReqDto {
        // 유효성 검사를 위한 어노테이션
        // 영문, 숫자는 되지만 한글은 안됨, 길이 최소 2~20자
        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "아이디는 영문, 숫자만 가능하며 2~10자로 입력해주세요.")
        @NotEmpty // null, "" 불가
        private String username;

        // 길이 최소 4~20자
        @NotEmpty
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자로 입력해주세요.")
        private String password;

        // 이메일 형식
        @Pattern(regexp = "^[a-zA-Z0-9]{2,10}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z\\.]{2,5}$", message = "이메일 형식에 맞게 입력해주세요.")
        @NotEmpty
        private String email;

        // 한글, 영문, 숫자, 특수문자, 길이 최소 2~20자
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{1,20}$", message = "이름은 한글, 영문, 숫자만 가능하며 1~20자로 입력해주세요.")
        @NotEmpty
        private String fullname;

        public User toEntity(BCryptPasswordEncoder passwordEncoder) {
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .fullname(fullname)
                    .role(UserEnum.CUSTOMER)
                    .build();
        }
    }
    
}
