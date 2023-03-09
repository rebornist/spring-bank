package shop.mtcoding.bank.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.bank.config.duumy.DummyObject;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.user.UserReqDto.JoinReqDto;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest extends DummyObject {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        dataSetting();
    }

    @Test
    public void signup_success_test() throws Exception {
        // given
        JoinReqDto dto = new JoinReqDto();
        dto.setUsername("love");
        dto.setPassword("1234");
        dto.setFullname("러브");
        dto.setEmail("love@nate.com");

        String requestBody = om.writeValueAsString(dto);                            
        // System.out.println("requestBody: " + requestBody);

        // when
        ResultActions result = mockMvc.perform(post("/api/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 성공: " + responseBody);

        // then
        result.andExpect(status().isCreated());
    }
    
    @Test
    public void signup_fail_test() throws Exception {
        // given
        JoinReqDto dto = new JoinReqDto();
        dto.setUsername("ssar");
        dto.setPassword("1234");
        dto.setFullname("쌀");
        dto.setEmail("ssar@nate.com");

        String requestBody = om.writeValueAsString(dto);                            
        // System.out.println("requestBody: " + requestBody);

        // when
        ResultActions result = mockMvc.perform(post("/api/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        String responseBody = result.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 실패: " + responseBody);

        // then
        result.andExpect(status().isBadRequest());
    }

    private void dataSetting() {
        userRepository.save(newUser("ssar", "쌀"));
    }
}
