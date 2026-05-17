package com.tw.step.readit.service;

import com.tw.step.readit.model.AddPostRequest;
import com.tw.step.readit.model.NewPost;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PostService {
    private final ArrayList<NewPost> posts;
    private int currentPostId = 1;

    public PostService() {
        this.posts = new ArrayList<>();
        NewPost newPost = new NewPost(this.currentPostId++, "Hello world", "Today is sunday", "dinesh8700", "own", "17-05-2026");
        this.posts.add(newPost);
    }

    public NewPost addPost(AddPostRequest post, String author) {
        NewPost newPost = new NewPost(this.currentPostId++, post.title(), post.body(), author, "own", post.date());
        this.posts.add(newPost);
        return newPost;
    }

    public ArrayList<NewPost> getPosts() {
        return this.posts;
    }

    public boolean deletePost(int id) {
        this.posts.removeIf(post -> post.id() == id);
        return true;
    }
}
