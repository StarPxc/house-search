package yuqiao.housesearch.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import yuqiao.housesearch.security.user.entity.User;
import yuqiao.housesearch.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/**
 * @author 浦希成
 * 2018/10/19
 */
@Component("loginAuthenticationSuccessHandler")
@Slf4j
public class LoginAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("登录成功，时间：{}，ip：{}，用户名：{}",
                DateUtil.getCurrentDateString(),
                request.getRemoteHost(),
                ((User) authentication.getPrincipal()).getUsername());

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(authentication.getPrincipal()));
    }
}
