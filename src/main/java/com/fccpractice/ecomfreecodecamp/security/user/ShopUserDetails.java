package com.fccpractice.ecomfreecodecamp.security.user;

import com.fccpractice.ecomfreecodecamp.model.Role;
import com.fccpractice.ecomfreecodecamp.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class ShopUserDetails implements UserDetails {

    private final User user; // The User object
    private final Collection<? extends GrantedAuthority> authorities;

    // Constructor to initialize the user and authorities
    public ShopUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    // Static method to create ShopUserDetails from a User entity
    public static ShopUserDetails buildUserDetails(User user) {
        // Convert roles into GrantedAuthorities
        Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // Convert Role to GrantedAuthority
                .collect(Collectors.toList());

        // Return new ShopUserDetails instance with the converted authorities
        return new ShopUserDetails(user, authorities);
    }

    // Expose the User object through this method
    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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

    public Long getId() {
        return user.getId(); // Return the user's ID
    }

    public String getEmail() {
        return user.getEmail();
    }
}
