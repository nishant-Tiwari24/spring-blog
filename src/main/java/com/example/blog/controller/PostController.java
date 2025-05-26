package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import com.example.blog.service.FileStorageService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    public PostController(PostService postService, UserService userService, FileStorageService fileStorageService) {
        this.postService = postService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Post());
        return "posts/form";
    }

    @PostMapping("/new")
    public String createPost(@Valid @ModelAttribute Post post, 
                           BindingResult result,
                           @RequestParam(value = "image", required = false) MultipartFile image,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        if (result.hasErrors()) {
            return "posts/form";
        }

        try {
            User author = userService.findByUsername(userDetails.getUsername());
            post.setAuthor(author);

            if (image != null && !image.isEmpty()) {
                String fileName = fileStorageService.storeFile(image);
                post.setImageUrl(fileName);
            }

            Post savedPost = postService.createPost(post);
            return "redirect:/posts/" + savedPost.getId();
        } catch (Exception e) {
            model.addAttribute("error", "Error creating post: " + e.getMessage());
            return "posts/form";
        }
    }

    @GetMapping("/{id}")
    public String showPost(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "posts/form";
    }

    @PostMapping("/{id}/edit")
    public String updatePost(@PathVariable Long id,
                           @Valid @ModelAttribute Post post,
                           BindingResult result,
                           @RequestParam(value = "image", required = false) MultipartFile image,
                           @RequestParam(value = "removeImage", required = false) Boolean removeImage,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        if (result.hasErrors()) {
            return "posts/form";
        }

        try {
            Post existingPost = postService.findById(id);
            
            // Check if the current user is the author
            if (!existingPost.getAuthor().getUsername().equals(userDetails.getUsername())) {
                return "redirect:/posts/" + id;
            }

            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());

            // Handle image upload/removal
            if (removeImage != null && removeImage) {
                if (existingPost.getImageUrl() != null) {
                    fileStorageService.deleteFile(existingPost.getImageUrl());
                    existingPost.setImageUrl(null);
                }
            } else if (image != null && !image.isEmpty()) {
                if (existingPost.getImageUrl() != null) {
                    fileStorageService.deleteFile(existingPost.getImageUrl());
                }
                String fileName = fileStorageService.storeFile(image);
                existingPost.setImageUrl(fileName);
            }

            postService.updatePost(existingPost);
            return "redirect:/posts/" + id;
        } catch (Exception e) {
            model.addAttribute("error", "Error updating post: " + e.getMessage());
            return "posts/form";
        }
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id,
                           @AuthenticationPrincipal UserDetails userDetails) {
        Post post = postService.findById(id);
        
        // Check if the current user is the author
        if (post.getAuthor().getUsername().equals(userDetails.getUsername())) {
            if (post.getImageUrl() != null) {
                fileStorageService.deleteFile(post.getImageUrl());
            }
            postService.deletePost(id);
        }
        
        return "redirect:/";
    }
} 