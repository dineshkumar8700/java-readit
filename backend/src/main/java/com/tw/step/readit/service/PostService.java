package com.tw.step.readit.service;

import com.tw.step.readit.model.*;
import com.tw.step.readit.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public FormattedPost addPost(AddPostRequest post, String author) {
        Post mongoPost = new Post(null, post.title(), post.body(), post.date(), author, new ArrayList<>());
        Post savedPost = this.postRepository.save(mongoPost);
        return formatPost(savedPost, author);
    }

    public List<FormattedPost> getPosts(String username) {
        List<Post> posts = this.postRepository.findAll();
        ArrayList<FormattedPost> formattedPost = new ArrayList<>();

        for (Post post : posts) {
            formattedPost.add(this.formatPost(post, username));
        }

        return formattedPost.reversed();
    }

    private FormattedPost formatPost(Post post, String username) {
        String type = post.author().equals(username) ? "own" : "subscribed";
        boolean isLiked = post.likedBy().contains(username);
        int likes = post.likedBy().size();

        return new FormattedPost(post, type, isLiked, likes);
    }

    public void deletePost(String id) {
        this.postRepository.deleteById(id);
    }

    public void toggleLike(String id, String username) {
        Optional<Post> optionalPost = this.postRepository.findById(id);

        if (optionalPost.isPresent()) {
            Post oldPost = optionalPost.get();

            List<String> updatedLikes = new ArrayList<>(oldPost.likedBy());
            if (updatedLikes.contains(username)) {
                updatedLikes.remove(username);
            } else {
                updatedLikes.add(username);
            }

            Post updatedPost = new Post(
                    oldPost.id(),
                    oldPost.title(),
                    oldPost.body(),
                    oldPost.date(),
                    oldPost.author(),
                    updatedLikes
            );

            this.postRepository.save(updatedPost);
        }
    }
}
