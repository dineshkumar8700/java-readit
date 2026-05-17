package com.tw.step.readit.controller;

import com.tw.step.readit.model.AddPostRequest;
import com.tw.step.readit.model.AddPostResponse;
import com.tw.step.readit.model.LoginResponse;
import com.tw.step.readit.model.NewPost;
import com.tw.step.readit.service.PostService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;
import java.util.ArrayList;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/posts")
    public ResponseEntity<ArrayList<NewPost>> posts() {
        return ResponseEntity
                .ok()
                .body(this.postService.getPosts());
    }

    @PostMapping("/api/add-post")
    public ResponseEntity<AddPostResponse> addPost(@RequestBody AddPostRequest post, @CookieValue("session_id") String author) {
        NewPost newPost = this.postService.addPost(post, author);

        return  ResponseEntity.ok().body(new AddPostResponse(newPost, true));
    }

    @DeleteMapping("/api/post")
    public ResponseEntity<LoginResponse> delete(@RequestBody JsonNode body) {
        int id = body.get("id").asInt();
        this.postService.deletePost(id);
        return ResponseEntity.ok().body(new LoginResponse(true));
    }
}
