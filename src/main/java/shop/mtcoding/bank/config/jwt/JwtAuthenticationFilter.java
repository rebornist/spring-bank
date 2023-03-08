package shop.mtcoding.bank.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.dto.user.UserReqDto.LoginReqDto;
import shop.mtcoding.bank.dto.user.UserRespDto.LoginRespDto;
import shop.mtcoding.bank.util.CustomResponseUtil;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/login"); // 시큐리티 기본 URL을 사용하지 않고, 내가 만든 URL을 사용하겠다.
        this.authenticationManager = authenticationManager;
    }
    
    // Post : /api/login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // 1. username, password 받아서
        // 2. 정상인지 로그인 시도를 해보는 것 (AuthenticationManager로 로그인 시도)
        // 3. PrincipalDetailsService가 세션에 담아준다.
        // 4. JWT 토큰을 만들어서 응답해준다.

        try {
            
            ObjectMapper om = new ObjectMapper();
            LoginReqDto loginReqDto = om.readValue(request.getInputStream(), LoginReqDto.class);

            // 강제 로그인
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginReqDto.getUsername(), loginReqDto.getPassword());

            // JWT를 사용한다 하더라도, 컨트롤러에 진입을 하면 시큐리티의 권한체크, 인증체크의 도움을 받을 수 있도록 세션을 만든다.
            // 해당 세션의 유효기간은 request, response가 끝날 때 까지이다.
            Authentication authentication = authenticationManager.authenticate(authenticationToken); // UserDetailsService의 loadUserByUsername() 함수가 실행된다.
            return authentication;
        } catch (Exception e) {
            // authenticationEntryPoint로 넘어간다.
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    // attemptAuthentication() 함수가 정상적으로 실행되면 실행된다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        String jwtToken = JwtProcess.createToken(loginUser);
        response.addHeader(JwtVo.HEADER, jwtToken);

        LoginRespDto loginRespDto = new LoginRespDto(loginUser.getUser());
        CustomResponseUtil.success(response, loginRespDto);
    }
}
