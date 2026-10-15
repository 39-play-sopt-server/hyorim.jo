package org.sopt.post.controller;

import org.sopt.global.response.ApiResponse;
import org.sopt.post.dto.CreatePostRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.UpdatePostRequest;
import org.sopt.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts") // TODO: @ApiV1 구현
public class PostController {
    private final PostService postService;

    public PostController(PostService service) {
        this.postService = service;
    }

    @PostMapping
    public ApiResponse<PostResponse> createPost(@RequestBody CreatePostRequest request) {
        return ApiResponse.created(postService.createPost(request));
    }

    @GetMapping
    public ApiResponse<List<PostResponse>> getPosts() {
        return ApiResponse.ok(postService.getPosts());
    }

    @GetMapping(path = "/{postId}")
    public ApiResponse<PostResponse> getPost(@PathVariable(name = "postId") Long postId) {
        return ApiResponse.ok(postService.getPost(postId));
    }

    @PatchMapping(path = "/{postId}")
    public ApiResponse<PostResponse> updatePost(@PathVariable(name = "postId") Long postId, @RequestBody UpdatePostRequest request) {
        return ApiResponse.ok(postService.updatePost(postId, request));
    }

    @DeleteMapping(path = "/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable(name = "postId") Long postId) {
        postService.deletePost(postId);
        return ApiResponse.ok();
    }
}