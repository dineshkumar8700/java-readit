package com.tw.step.readit.controller;

import com.tw.step.readit.model.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class Post {
    @GetMapping("/api/posts")
    public ResponseEntity<ArrayList<PostResponse>> posts() {
        System.out.println("Get Posts request came");
        ArrayList<PostResponse> posts = new ArrayList<>();
        posts.add(new PostResponse("Hello world", "Today is sundayy", "dinesh8700", "own", "17-05-2026"));

        return ResponseEntity
                .ok()
                .body(posts);
    }
}
