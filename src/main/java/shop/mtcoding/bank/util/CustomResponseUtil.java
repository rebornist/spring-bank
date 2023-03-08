package shop.mtcoding.bank.util;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.bank.dto.ResponseDto;

public class CustomResponseUtil {

    private static final Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

    public static void success(HttpServletResponse response, Object dto) {
        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(HttpStatus.OK.value(), "로그인 성공", dto);
            String responseBody = om.writeValueAsString(responseDto);
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().println(responseBody); // 우아하게 메시지를 포장하는 공통적인 응답 DTO 만들기
        } catch (Exception e) {
            log.error("인증이 필요한 요청에 대한 응답 처리 중 오류 발생", e);
        }

    }

    public static void unAuthentication(HttpServletResponse response, String msg) {
        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(HttpStatus.UNAUTHORIZED.value(), msg, null);
            String responseBody = om.writeValueAsString(responseDto);
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(401);
            response.getWriter().println(responseBody); // 우아하게 메시지를 포장하는 공통적인 응답 DTO 만들기
        } catch (Exception e) {
            log.error("인증이 필요한 요청에 대한 응답 처리 중 오류 발생", e);
        }

    }

    public static void unAuthorizaion(HttpServletResponse response, String msg) {
        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(HttpStatus.FORBIDDEN.value(), msg, null);
            String responseBody = om.writeValueAsString(responseDto);
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(403);
            response.getWriter().println(responseBody); // 우아하게 메시지를 포장하는 공통적인 응답 DTO 만들기
        } catch (Exception e) {
            log.error("인증이 필요한 요청에 대한 응답 처리 중 오류 발생", e);
        }

    }
    
}
