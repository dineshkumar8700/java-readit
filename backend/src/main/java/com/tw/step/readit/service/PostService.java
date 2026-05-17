package com.tw.step.readit.service;

import com.tw.step.readit.model.AddPostRequest;
import com.tw.step.readit.repository.Post;
import com.tw.step.readit.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post addPost(AddPostRequest post, String author) {
        Post newPost = new Post(null, post.title(), post.body(), post.date(), author, new ArrayList<>());
        return this.postRepository.save(newPost);
    }

    public List<Post> getPosts() {
        return this.postRepository.findAll().reversed();
    }

    public void deletePost(int id) {
        System.out.println("Deleting");
    }
}
