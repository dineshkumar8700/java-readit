package com.tw.step.readit.controller;

import com.tw.step.readit.model.AddPostRequest;
import com.tw.step.readit.model.AddPostResponse;
import com.tw.step.readit.model.FormattedPost;
import com.tw.step.readit.model.LoginResponse;
import com.tw.step.readit.repository.Post;
import com.tw.step.readit.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/posts")
    public ResponseEntity<List<FormattedPost>> posts(@CookieValue("session_id") String username) {
        return ResponseEntity
                .ok()
                .body(this.postService.getPosts(username));
    }

    @PostMapping("/api/add-post")
    public ResponseEntity<AddPostResponse> addPost(@RequestBody AddPostRequest post, @CookieValue("session_id") String author) {
        FormattedPost newPost = this.postService.addPost(post, author);
        return ResponseEntity.ok().body(new AddPostResponse(newPost, true));
    }

    @DeleteMapping("/api/post")
    public ResponseEntity<LoginResponse> delete(@RequestBody JsonNode body) {
        String id = body.get("id").asString();
        this.postService.deletePost(id);
        return ResponseEntity.ok().body(new LoginResponse(true));
    }

    @PostMapping("/api/toggle-like")
    public ResponseEntity<LoginResponse> get(@RequestBody JsonNode body, @CookieValue("session_id") String username) {
        String id = body.get("postId").asString();
        this.postService.toggleLike(id, username);
        return ResponseEntity.ok().body(new LoginResponse(true));
    }
}
