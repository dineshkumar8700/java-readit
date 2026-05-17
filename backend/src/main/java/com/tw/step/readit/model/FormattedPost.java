package com.tw.step.readit.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tw.step.readit.repository.Post;

public record FormattedPost(
        @JsonUnwrapped
        Post post,
        String type,
        boolean isLiked,
        int likes
) {
}
