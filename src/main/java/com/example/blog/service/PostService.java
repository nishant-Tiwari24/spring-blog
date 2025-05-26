package com.example.blog.service;

import com.example.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Post createPost(Post post);
    Post updatePost(Post post);
    void deletePost(Long id);
    Post findById(Long id);
    Page<Post> findAllPosts(Pageable pageable);
} 