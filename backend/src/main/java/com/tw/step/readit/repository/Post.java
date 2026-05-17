package com.tw.step.readit.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "posts")
public record Post(
        @Id String id,
        String title,
        String body,
        String date,
        String author,
        List<String> likedBy
) {
}