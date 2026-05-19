package com.tw.step.readit.controller;

import com.tw.step.readit.model.LoggedInUser;
import com.tw.step.readit.model.UserRegistrationRequest;
import com.tw.step.readit.service.AppUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AppUserDetailsService appUserDetailsService;

    public AuthController(AppUserDetailsService appUserDetailsService) {
        this.appUserDetailsService = appUserDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> post(@RequestBody UserRegistrationRequest user) {
        this.appUserDetailsService.registerUser(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/whoami")
    public ResponseEntity<LoggedInUser> whoAmI(Authentication authentication) {
        String username = authentication.getName();

        return ResponseEntity.ok().body(new LoggedInUser(username));
    }
}
