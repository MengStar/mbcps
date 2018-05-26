package meng.xing.gateway.security;

import meng.xing.common.User.ResponseUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * UserDetails的实现类，可以定义一系列验证
 * 主要提供验证信息给Security
 */
public class UserDetailsIml implements UserDetails {
    private final String username;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String nickname;
    private final boolean isMain;
    private final String mainUsername;

    public UserDetailsIml(ResponseUser responseUser) {
        username = responseUser.getUsername();
        nickname = responseUser.getNickname();
        isMain = responseUser.isMain();
        mainUsername = responseUser.getMainUsername();
        Set<GrantedAuthority> roleSet = new HashSet<>();
        roleSet.add(new SimpleGrantedAuthority(responseUser.getRole()));
        authorities = roleSet;

    }

    //返回分配给用户的角色列表
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "password";
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 账户是否未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 密码是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账户是否激活
    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getMainUsername() {
        return mainUsername;
    }

    public boolean isMain() {
        return isMain;
    }

    public String getNickname() {
        return nickname;
    }
}

