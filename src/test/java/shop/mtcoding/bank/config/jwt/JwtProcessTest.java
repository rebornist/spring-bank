package shop.mtcoding.bank.config.jwt;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

public class JwtProcessTest {
    

    @Test
    public void create_test() throws Exception {
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);
        
        // when
        String jwtToken = JwtProcess.createToken(loginUser);
        // System.out.println("테스트 : " + jwtToken);

        // then
        assertTrue(jwtToken.startsWith(JwtVo.TOKEN_PREFIX));
    }

    @Test
    public void verify_test() throws Exception {
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);
        String jwtToken = JwtProcess.createToken(loginUser);
        
        // when
        LoginUser verifyUser = JwtProcess.verifyToken(jwtToken.replace(JwtVo.TOKEN_PREFIX, ""));
        // System.out.println("테스트 : " + verifyUser.getUser().getId());
        
        // then
        assertTrue(verifyUser.getUser().getId() == loginUser.getUser().getId());
        assertTrue(verifyUser.getUser().getRole() == loginUser.getUser().getRole());
    }

}
