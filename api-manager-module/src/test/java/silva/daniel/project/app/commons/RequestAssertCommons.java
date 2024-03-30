package silva.daniel.project.app.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import silva.daniel.project.app.domain.client.FailureResponse;
import silva.daniel.project.app.web.CreditCardController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CreditCardController.class)
public abstract class RequestAssertCommons {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    public abstract String url();

    public final void failurePostAssert(Object request, FailureResponse response, ResultMatcher statusMatcher) throws Exception {
        mockMvc.perform(post(url())
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(request)))
                .andExpect(statusMatcher)
                .andExpect(jsonPath("$.message").value(response.getMessage()))
                .andExpect(jsonPath("$.statusCode").value(response.getStatusCode()));
    }

    public final void successPostAssert(Object request, ResultMatcher statusMatcher) throws Exception {
        mockMvc.perform(post(url())
                .contentType("application/json")
                .content(mapper.writeValueAsString(request)))
                .andExpect(statusMatcher);
    }
}
