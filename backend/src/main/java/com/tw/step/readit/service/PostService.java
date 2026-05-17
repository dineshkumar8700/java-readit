package com.tw.step.readit.service;

import com.tw.step.readit.model.AddPostRequest;
import com.tw.step.readit.model.NewPost;
import com.tw.step.readit.repository.Post;
import com.tw.step.readit.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final ArrayList<NewPost> posts;
    private final PostRepository postRepository;
    private int currentPostId = 1;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
        this.posts = new ArrayList<>();
        NewPost newPost = new NewPost(this.currentPostId++, "Hello world", "Today is sunday", "dinesh8700", "own", "17-05-2026");
        this.posts.add(newPost);
    }

    public NewPost addPost(AddPostRequest post, String author) {
        NewPost newPost = new NewPost(this.currentPostId++, post.title(), post.body(), author, "own", post.date());
        this.posts.add(newPost);
        return newPost;
    }

    public List<Post> getPosts() {
        return this.postRepository.findAll();
    }

    public void deletePost(int id) {
        this.posts.removeIf(post -> post.id() == id);
    }
}
