package com.vinesmario.microservice.server.common.web.rest.errors;

import com.vinesmario.microservice.server.uaa.UaaServerApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.zalando.problem.spring.web.advice.MediaTypes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExceptionTranslator controller advice.
 *
 * @see ExceptionTranslator
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UaaServerApplication.class})
public class ExceptionTranslatorIntTest {

    @Autowired
    private ExceptionTranslatorTestController controller;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter)
                .build();
    }

    @Test
    public void testConcurrencyFailure() throws Exception {
        mockMvc.perform(get("/test/concurrency-failure"))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaTypes.PROBLEM))
                .andExpect(jsonPath("$.parameters.message").value(ErrorConstants.ERR_CONCURRENCY_FAILURE));
    }

    @Test
    public void testMethodArgumentNotValid() throws Exception {
        mockMvc.perform(post("/test/method-argument").content("{}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaTypes.PROBLEM))
                .andExpect(jsonPath("$.parameters.message").value(ErrorConstants.ERR_VALIDATION))
                .andExpect(jsonPath("$.parameters.fieldErrors.[0].objectName").value("testDTO"))
                .andExpect(jsonPath("$.parameters.fieldErrors.[0].field").value("test"))
                .andExpect(jsonPath("$.parameters.fieldErrors.[0].message").value("NotNull"));
    }

    @Test
    public void testParameterizedError() throws Exception {
        mockMvc.perform(get("/test/parameterized-error"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaTypes.PROBLEM))
                .andExpect(jsonPath("$.parameters.message").value("test parameterized error"))
                .andExpect(jsonPath("$.parameters.params.param0").value("param0_value"))
                .andExpect(jsonPath("$.parameters.params.param1").value("param1_value"));
    }

    @Test
    public void testParameterizedError2() throws Exception {
        mockMvc.perform(get("/test/parameterized-error2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaTypes.PROBLEM))
                .andExpect(jsonPath("$.parameters.message").value("test parameterized error"))
                .andExpect(jsonPath("$.parameters.params.foo").value("foo_value"))
                .andExpect(jsonPath("$.parameters.params.bar").value("bar_value"));
    }

    @Test
    public void testMissingServletRequestPartException() throws Exception {
        mockMvc.perform(get("/test/missing-servlet-request-part"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaTypes.PROBLEM))
                .andExpect(jsonPath("$.parameters.message").value("error.http.400"));
    }

    @Test
    public void testMissingServletRequestParameterException() throws Exception {
        mockMvc.perform(get("/test/missing-servlet-request-parameter"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaTypes.PROBLEM))
                .andExpect(jsonPath("$.parameters.message").value("error.http.400"));
    }

    @Test
    public void testAccessDenied() throws Exception {
        mockMvc.perform(get("/test/access-denied"))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaTypes.PROBLEM))
                .andExpect(jsonPath("$.detail").value("test access denied!"))
                .andExpect(jsonPath("$.parameters.message").value("error.http.403"));
    }

    @Test
    public void testUnauthorized() throws Exception {
        mockMvc.perform(get("/test/unauthorized"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaTypes.PROBLEM))
                .andExpect(jsonPath("$.detail").value("test authentication failed!"))
                .andExpect(jsonPath("$.parameters.message").value("error.http.401"))
                .andExpect(jsonPath("$.parameters.path").value("/test/unauthorized"));
    }

    @Test
    public void testMethodNotSupported() throws Exception {
        mockMvc.perform(post("/test/access-denied"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaTypes.PROBLEM))
                .andExpect(jsonPath("$.detail").value("Request method 'POST' not supported"))
                .andExpect(jsonPath("$.parameters.message").value("error.http.405"));
    }

    @Test
    public void testExceptionWithResponseStatus() throws Exception {
        mockMvc.perform(get("/test/response-status"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaTypes.PROBLEM))
                .andExpect(jsonPath("$.title").value("test response status"))
                .andExpect(jsonPath("$.parameters.message").value("error.http.400"));
    }

    @Test
    public void testInternalServerError() throws Exception {
        mockMvc.perform(get("/test/internal-server-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaTypes.PROBLEM))
                .andExpect(jsonPath("$.title").value("Internal Server Error"))
                .andExpect(jsonPath("$.parameters.message").value("error.http.500"));
    }

}
