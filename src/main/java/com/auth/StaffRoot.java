package com.auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.entity.Staff;

import lombok.Builder;

@SuppressWarnings("serial")
@Builder
public class StaffRoot implements UserDetails {
    private Staff staff;
    private List<GrantedAuthority> authorities;

    public static StaffRoot create(Staff staff) {
        return StaffRoot.builder()
                .staff(staff)
                .authorities(Arrays
                        .stream(staff.getRole().getName().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.staff.getPassword();
    }

    @Override
    public String getUsername() {
        return this.staff.getUsername();
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
}
