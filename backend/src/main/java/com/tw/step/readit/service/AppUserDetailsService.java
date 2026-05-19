package com.tw.step.readit.service;

import com.tw.step.readit.model.UserRegistrationRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.ConcurrentHashMap;

public class AppUserDetailsService implements UserDetailsService {
    private ConcurrentHashMap<String, UserDetails> users;
    private PasswordEncoder passwordEncoder;

    public AppUserDetailsService(ConcurrentHashMap<String, UserDetails> users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = this.users.get(username);
        if (userDetails == null) throw new UsernameNotFoundException("User not found: {}".formatted(username));

        return userDetails;
    }

    public void registerUser(UserRegistrationRequest user) {
        UserDetails newUser = User.builder()
                .passwordEncoder(this.passwordEncoder::encode)
                .username(user.username())
                .password(user.password())
                .build();

        this.users.put(newUser.getUsername(), newUser);
    }
}
