package com.tw.step.readit.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository
        extends MongoRepository<Post, String> {
}   