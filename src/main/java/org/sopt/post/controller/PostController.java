package org.sopt.post.controller;

import org.sopt.global.response.ApiResponse;
import org.sopt.post.dto.*;
import org.sopt.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// TODO: @ApiV1 구현
public class PostController implements PostApi {
    private final PostService postService;

    public PostController(PostService service) {
        this.postService = service;
    }

    @Override
    public ResponseEntity<ApiResponse<PostResponse>> createPost(Long memberId, CreatePostRequest request) {
        return ApiResponse.created(postService.createPost(memberId, request));
    }

    @Override
    public ResponseEntity<ApiResponse<PostPageResponse>> getPosts(int page, int size, Long prefixId) {
        return ApiResponse.ok(postService.getPosts(page, size, prefixId));
    }

    @Override
    public ResponseEntity<ApiResponse<PostResponse>> getPost(Long postId) {
        return ApiResponse.ok(postService.getPost(postId));
    }

    @Override
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(Long postId, UpdatePostRequest request) {
        return ApiResponse.ok(postService.updatePost(postId, request));
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> deletePost(Long postId) {
        postService.deletePost(postId);
        return ApiResponse.ok();
    }
}