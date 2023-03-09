package shop.mtcoding.bank.config.jwt;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.bank.config.duumy.DummyObject;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.user.UserReqDto.LoginReqDto;

@Transactional // 테스트가 끝나면 롤백
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class JwtAuthenticationFilterTest extends DummyObject {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() throws Exception {
        userRepository.save(newUser("ssar", "1234"));
    } 

    @Test
    public void successfulAuthentication_test() throws Exception {
        // given
        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setUsername("ssar");
        loginReqDto.setPassword("1234");
        String requestBody = objectMapper.writeValueAsString(loginReqDto);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        String jwtToken = resultActions.andReturn().getResponse().getHeader(JwtVo.HEADER);
        System.out.println("테스트 성공: " + responseBody);
        System.out.println("테스트 성공: " + jwtToken);
        
        // then
        resultActions.andExpect(status().isOk());
        assertNotNull(jwtToken);
        assertTrue(jwtToken.startsWith(JwtVo.TOKEN_PREFIX));
        resultActions.andExpect(jsonPath("$.data.username").value("ssar"));
    }

    @Test
    public void unsuccessfulAuthentication_test() throws Exception {
         // given
         LoginReqDto loginReqDto = new LoginReqDto();
         loginReqDto.setUsername("ssar");
         loginReqDto.setPassword("12341");
         String requestBody = objectMapper.writeValueAsString(loginReqDto);
         System.out.println("테스트 : " + requestBody);
 
         // when
         ResultActions resultActions = mvc.perform(post("/api/login")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(requestBody));
         String responseBody = resultActions.andReturn().getResponse().getContentAsString();
         String jwtToken = resultActions.andReturn().getResponse().getHeader(JwtVo.HEADER);
         System.out.println("테스트 성공: " + responseBody);
         System.out.println("테스트 성공: " + jwtToken);
        
        // then
        resultActions.andExpect(status().isUnauthorized());
    }
    
}
