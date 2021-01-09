package com.anonymizer.auth.security;

import com.anonymizer.auth.models.Role;
import com.anonymizer.auth.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.stream.Collectors;

public class JpaUserDetail implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Autowired
    PasswordEncoder passwordEncoder;

    private User user;

    public JpaUserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return user.getRoles()
                .stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return  this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.isNotExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return  this.user.isNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return  this.user.isNotCredentialsExpired() ;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }
}
