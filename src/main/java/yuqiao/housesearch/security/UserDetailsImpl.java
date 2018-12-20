package yuqiao.housesearch.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import yuqiao.housesearch.security.user.entity.User;

import java.util.Collection;

/**
 * @author 浦希成
 * 2018/10/18
 * 用于自定义认证逻辑的类
 */
public class UserDetailsImpl extends User implements UserDetails {


    /**
     * 可以是使用工具根据字符串生成权限 例如 "admin,guest"类型的字符串
     *
     * @return Collection
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(getAuthors());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getStatus() == 1;
    }
}
