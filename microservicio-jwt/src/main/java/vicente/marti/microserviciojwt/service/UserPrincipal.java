package vicente.marti.microserviciojwt.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vicente.marti.microserviciojwt.entity.UserEntity;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal build(UserEntity userEntity) {
        List<SimpleGrantedAuthority> authorities =
                userEntity.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.name())).toList();
        return new UserPrincipal(userEntity.getUsername(), userEntity.getEmail(), userEntity.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }

    public String getEmail() {
        return email;
    }
}
