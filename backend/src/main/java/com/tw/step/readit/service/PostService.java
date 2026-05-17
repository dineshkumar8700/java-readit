package com.tw.step.readit.service;

import com.tw.step.readit.model.AddPostRequest;
import com.tw.step.readit.model.NewPost;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PostService {
    private final ArrayList<NewPost> posts;

    public PostService() {
        this.posts = new ArrayList<>();
        NewPost newPost = new NewPost("Hello world", "Today is sunday", "dinesh8700", "own", "17-05-2026");
        this.posts.add(newPost);
    }

    public NewPost addPost(AddPostRequest post) {
        NewPost newPost = new NewPost(post.title(), post.body(), "", "", post.date());
        this.posts.add(newPost);
        return newPost;
    }

    public ArrayList<NewPost> getPosts() {
        return this.posts;
    }
}
