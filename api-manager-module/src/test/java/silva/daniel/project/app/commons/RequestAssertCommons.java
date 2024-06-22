package silva.daniel.project.app.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import silva.daniel.project.app.domain.client.FailureResponse;
import silva.daniel.project.app.web.CreditCardController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CreditCardController.class)
public abstract class RequestAssertCommons {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    public abstract String url();

    public final void failurePostAssert(Object request, FailureResponse response, ResultMatcher statusMatcher) throws Exception {
        successRequest(post(url())
                .contentType("application/json")
                .content(mapper.writeValueAsString(request)),
                statusMatcher,
                jsonPath("$.message").value(response.getMessage()),
                jsonPath("$.statusCode").value(response.getStatusCode()));
    }

    public final void failurePutAssert(Object request, FailureResponse response, ResultMatcher statusMatcher) throws Exception {
        successRequest(put(url())
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(request)),
                statusMatcher,
                jsonPath("$.message").value(response.getMessage()),
                jsonPath("$.statusCode").value(response.getStatusCode()));
    }

    public final void successPostAssert(Object request, ResultMatcher... statusMatcher) throws Exception {
        successRequest(post(url())
                .contentType("application/json")
                .content(mapper.writeValueAsString(request)), statusMatcher);
    }

    public final void successPutAssert(Object request, ResultMatcher... statusMatcher) throws Exception {
        successRequest(put(url())
                .contentType("application/json")
                .content(mapper.writeValueAsString(request)), statusMatcher);
    }

    public final void successGetAssert(final Object[] parameters, final ResultMatcher... statusMatcher) throws Exception {
        successRequest(get(url(), parameters), statusMatcher);
    }

    public void failureGetAssert(Object[] objects, FailureResponse response, ResultMatcher statusMatcher) throws Exception {
        successRequest(get(url(), objects), statusMatcher,
                       jsonPath("$.message").value(response.getMessage()),
                       jsonPath("$.statusCode").value(response.getStatusCode()));
    }

    private void successRequest(final RequestBuilder requestBuilder, ResultMatcher... statusMatcher) throws Exception {
        mockMvc.perform(requestBuilder)
                .andExpectAll(statusMatcher);
    }
}
