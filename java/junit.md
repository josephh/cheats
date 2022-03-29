# Testing with JUnit and Spring

## External configuration
```java
// Use @TestPropertySource to fake some external configuration
package uk.co.britishgas.uaa.authentication.handlers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.britishgas.uaa.authentication.exception.PasswordLessAuthenticationException;

@TestPropertySource(properties = {"magicLink.failureUrl = /some/fictional/uri?error=%s&state=%s"})
@RunWith(SpringJUnit4ClassRunner.class)
public class PasswordLessAuthenticationFailureHandlerTest {

  @Mock HttpServletRequest mockRequest;

  @Mock HttpServletResponse mockResponse;

  @Value("${magicLink.failureUrl}")
  private String defaultFailureUrl;

  private String dummyExcpMessage = "test dummy exception msg";
  private String encodedDummyExcpMessage = "test+dummy+exception+msg";
  private String dummyState = "/dummy state?passwordless=true";
  private String encodedDummyState = "%2Fdummy+state%3Fpasswordless%3Dtrue";

  @Autowired @InjectMocks private PasswordLessAuthenticationFailureHandler handler;

  @Configuration
  static class Config {
    @Bean
    public PasswordLessAuthenticationFailureHandler handler() {
      return new PasswordLessAuthenticationFailureHandler();
    }
  }

  @Test
  public void onAuthenticationFailure() throws Exception {
    handler.onAuthenticationFailure(
        mockRequest,
        mockResponse,
        new PasswordLessAuthenticationException(dummyExcpMessage, dummyState));
    verify(mockResponse, times(1))
        .sendRedirect(
            "/some/fictional/uri?error=" + encodedDummyExcpMessage + "&state=" + encodedDummyState);
  }
}
```

## JUnit 'Rules'
Use `@Rule` to specify something to invoke before and after a test method has run.  
