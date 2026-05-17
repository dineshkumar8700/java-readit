package com.tw.step.readit.service;

import com.tw.step.readit.model.AddPostRequest;
import com.tw.step.readit.model.FormattedPost;
import com.tw.step.readit.repository.Post;
import com.tw.step.readit.repository.PostRepository;
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

    public Post addPost(AddPostRequest post, String author) {
        Post newPost = new Post(null, post.title(), post.body(), post.date(), author, new ArrayList<>());
        return this.postRepository.save(newPost);
    }

    public List<FormattedPost> getPosts(String username) {
        List<Post> posts = this.postRepository.findAll();
        ArrayList<FormattedPost> formattedPost = new ArrayList<>();

        for (Post post : posts) {
            String type = post.author().equals(username) ? "own" : "subscribed";
            boolean isLiked = post.likedBy().contains(username);
            int likes = post.likedBy().size();

            formattedPost.add(new FormattedPost(post, type, isLiked, likes));
        }

        return formattedPost.reversed();
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
