package yuqiao.housesearch.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import yuqiao.housesearch.common.enums.ErrorCode;
import yuqiao.housesearch.util.RestUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 浦希成
 * 2018/10/19
 */
@Component("loginAuthenticationFailureHandler")
public class LoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.getWriter().write(objectMapper.writeValueAsString(RestUtil.error(ErrorCode.LOGIN_FAIL)));

    }


}
