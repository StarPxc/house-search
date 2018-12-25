package yuqiao.housesearch.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author 浦希成
 * 2018-12-16
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private AuthenticationSuccessHandler loginAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler loginAuthenticationFailureHandler;
    @Autowired
    private UserDetailsService userDetailsServiceImpl;
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //用表单登录，所有的请求都需要进行身份认证
        http
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/authentication")
                .successHandler(loginAuthenticationSuccessHandler)
                .failureHandler(loginAuthenticationFailureHandler)
                .and()
                .rememberMe()
                .userDetailsService(userDetailsServiceImpl)
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login",
                        "/login/authentication",
                        "/img/**",
                        "/craw/**",
                        "/**",

                        "/swagger-ui.html",
                        "/webjars/**",
                        "/v2/**",
                        "/swagger-resources/**",
                "/**")

                .permitAll()
                .anyRequest()
                .authenticated()
        ;
    }
}
