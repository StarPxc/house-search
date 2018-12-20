package yuqiao.housesearch.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import yuqiao.housesearch.security.user.entity.User;
import yuqiao.housesearch.security.user.mapper.UserMapper;


/**
 * @author 浦希成
 * 2018-12-16
 */
@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
        if (user==null) {
            log.error("用户不存在");
            throw new UsernameNotFoundException("用户不存在");

        }
        UserDetailsImpl userDetailsImpl = new UserDetailsImpl();
        BeanUtils.copyProperties(user, userDetailsImpl);
        return userDetailsImpl;
    }
}
