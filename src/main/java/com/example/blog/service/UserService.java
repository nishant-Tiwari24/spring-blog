package com.example.blog.service;

import com.example.blog.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    User createUser(User user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 