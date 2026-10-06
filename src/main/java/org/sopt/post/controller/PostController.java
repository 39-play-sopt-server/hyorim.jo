package org.sopt.post.controller;

import org.sopt.post.domain.Post;
import org.sopt.post.service.PostService;

import java.util.List;

public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void createPost(String title, String content) {
        service.createPost(title, content);
    }

    public List<Post> getPosts() {
        return service.getPosts();
    }

    public Post getPost(int postId) {
        return service.getPost(postId);
    }

    public void updatePost(int postId, String title, String content) {
        service.updatePost(postId, title, content);
    }

    public void deletePost(int postId) {
        service.deletePost(postId);
    }
}
