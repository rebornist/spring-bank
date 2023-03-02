package shop.mtcoding.bank.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@AutoConfigureMockMvc // MockMvc를 사용하기 위해 필요. Mock(가짜) 환경에 MVC를 사용할 수 있게 해줌.
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class SecurityConfigTest {

    // 가짜 환경에 등록된 MockMvc를 주입받음.
    @Autowired
    private MockMvc mvc;

    // 서버는 일관성 있게 에러가 리턴되어야 한다.
    // 내가 모르는 에러가 프론트에 노출되면 안된다.
    @Test
    public void authentication_test() throws Exception {
        // given

        // when

        ResultActions result = mvc.perform(get("/api/s/hello"));
        String responseBody = result.andReturn().getResponse().getContentAsString();
        int httpStatusCode = result.andReturn().getResponse().getStatus();
        System.out.println("테스트 : " + responseBody);

        // then
        assertThat(httpStatusCode).isEqualTo(401);

    }

    @Test
    public void authorization_test() throws Exception {
        // given

        // when

        // then

    }
    
}
