package com.tw.step.readit.controller;

import com.tw.step.readit.model.loggedInResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class Auth {

    @GetMapping("/is-logged-in")
    public ResponseEntity<loggedInResponse> isLoggedIn() {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new loggedInResponse(false));
    }
}
