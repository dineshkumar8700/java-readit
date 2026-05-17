package com.tw.step.readit.controller;

import com.tw.step.readit.model.AddPostRequest;
import com.tw.step.readit.model.AddPostResponse;
import com.tw.step.readit.model.NewPost;
import com.tw.step.readit.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
public class Post {
    private final PostService postService;

    public Post(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/posts")
    public ResponseEntity<ArrayList<NewPost>> posts() {
        return ResponseEntity
                .ok()
                .body(this.postService.getPosts());
    }

    @PostMapping("/api/add-post")
    public ResponseEntity<AddPostResponse> addPost(@RequestBody AddPostRequest post) {
        NewPost newPost = this.postService.addPost(post);
        return  ResponseEntity.ok().body(new AddPostResponse(newPost, true));
    }
}
