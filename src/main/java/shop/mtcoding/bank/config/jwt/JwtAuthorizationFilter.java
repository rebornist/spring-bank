package shop.mtcoding.bank.config.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import shop.mtcoding.bank.config.auth.LoginUser;

/*
 * 모든 주소에서 동작함 (토큰 검증)
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(javax.servlet.http.HttpServletRequest request,
			javax.servlet.http.HttpServletResponse response, javax.servlet.FilterChain chain)
			throws java.io.IOException, javax.servlet.ServletException {
		if (isHeaderVerify(request, response)) {
			// 토큰이 존재함
			String token = request.getHeader(JwtVo.HEADER).replace(JwtVo.TOKEN_PREFIX, "");
			LoginUser loginUser = JwtProcess.verifyToken(token);

			// 임시 세션 생성(UserDetails or username)
			Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null,
					loginUser.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);

		}
		chain.doFilter(request, response);
	}

	private boolean isHeaderVerify(HttpServletRequest request, HttpServletResponse response) {
		String header = request.getHeader(JwtVo.HEADER);
		if (header == null || !header.startsWith(JwtVo.TOKEN_PREFIX)) {
			return false;
		}
		return true;
	}

}
