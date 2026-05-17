package com.tw.step.readit.controller;

import com.tw.step.readit.model.LoginRequest;
import com.tw.step.readit.model.LoginResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Auth {

    @GetMapping("/is-logged-in")
    public ResponseEntity<LoginResponse> isLoggedIn(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) return sendLoginResponse(false);

        for (Cookie cookie: cookies) {
            if(cookie.getName().equals("session_id")) return sendLoginResponse(true);
        }

        return sendLoginResponse(false);
    }

    private static ResponseEntity<LoginResponse> sendLoginResponse(boolean success) {
        return ResponseEntity.ok().body(new LoginResponse(success));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> handleLogin(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        String username = loginRequest.username();
        String password = loginRequest.password();

        Cookie cookie = setSessionId(username);

        response.addCookie(cookie);
        return ResponseEntity.ok().body(new LoginResponse(true));
    }

    private static Cookie setSessionId(String username) {
        Cookie cookie = new Cookie("session_id", username);
        cookie.setMaxAge(86400);
        cookie.setPath("/");

        return cookie;
    }
}
