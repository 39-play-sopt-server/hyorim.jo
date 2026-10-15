package org.sopt.post.controller;

import org.sopt.global.response.ApiResponse;
import org.sopt.post.dto.CreatePostRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.UpdatePostRequest;
import org.sopt.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// TODO: @ApiV1 구현
public class PostController implements PostApi {
    private final PostService postService;

    public PostController(PostService service) {
        this.postService = service;
    }

    @Override
    public ApiResponse<PostResponse> createPost(CreatePostRequest request) {
        return ApiResponse.created(postService.createPost(request));
    }

    @Override
    public ApiResponse<List<PostResponse>> getPosts() {
        return ApiResponse.ok(postService.getPosts());
    }

    @Override
    public ApiResponse<PostResponse> getPost(Long postId) {
        return ApiResponse.ok(postService.getPost(postId));
    }

    @Override
    public ApiResponse<PostResponse> updatePost(Long postId, UpdatePostRequest request) {
        return ApiResponse.ok(postService.updatePost(postId, request));
    }

    @Override
    public ApiResponse<Void> deletePost(Long postId) {
        postService.deletePost(postId);
        return ApiResponse.ok();
    }
}