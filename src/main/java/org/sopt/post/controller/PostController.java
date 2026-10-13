package org.sopt.post.controller;

import org.sopt.global.code.CommonErrorCode;
import org.sopt.global.exception.GeneralException;
import org.sopt.global.response.ApiResponse;
import org.sopt.post.dto.CreatePostRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.UpdatePostRequest;
import org.sopt.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService service) {
        this.postService = service;
    }

    @PostMapping
    public ApiResponse<PostResponse> createPost(@RequestBody CreatePostRequest request) {
        try {
            return ApiResponse.created(postService.createPost(request));
        } catch (GeneralException e) {
            return ApiResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            return ApiResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ApiResponse<List<PostResponse>> getPosts() {
        try {
            return ApiResponse.ok(postService.getPosts());
        } catch (GeneralException e) {
            return ApiResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            return ApiResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{postId}")
    public ApiResponse<PostResponse> getPost(@PathVariable(name = "postId") Long postId) {
        try {
            return ApiResponse.ok(postService.getPost(postId));
        } catch (GeneralException e) {
            return ApiResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            return ApiResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(path = "/{postId}")
    public ApiResponse<Void> updatePost(@PathVariable(name = "postId") Long postId, @RequestBody UpdatePostRequest request) {
        try {
            postService.updatePost(postId, request);
            return ApiResponse.ok();
        } catch (GeneralException e) {
            return ApiResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            return ApiResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/{postId}")
    public ApiResponse<Void> deletePost(@PathVariable(name = "postId") Long postId) {
        try {
            postService.deletePost(postId);
            return ApiResponse.ok();
        } catch (GeneralException e) {
            return ApiResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            return ApiResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}