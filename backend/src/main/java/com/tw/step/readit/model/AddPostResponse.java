package com.tw.step.readit.model;

import com.tw.step.readit.repository.Post;

public record AddPostResponse(Post post, boolean success) {
}
