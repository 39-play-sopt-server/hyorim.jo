package org.sopt.post.controller;

import org.sopt.global.code.CommonErrorCode;
import org.sopt.global.exception.GeneralException;
import org.sopt.global.response.ApiResponse;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.service.PostService;

import java.util.List;

public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public ApiResponse<PostResponse> createPost(String category, String title, String content, String author) {
        try {
            return ApiResponse.created(service.createPost(category, title, content, author));
        } catch (GeneralException e) {
            return ApiResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            return ApiResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse<List<PostResponse>> getPosts() {
        try {
            return ApiResponse.ok(service.getPosts());
        } catch (GeneralException e) {
            return ApiResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            return ApiResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse<PostResponse> getPost(int postId) {
        try {
            return ApiResponse.ok(service.getPost(postId));
        } catch (GeneralException e) {
            return ApiResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            return ApiResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse<Void> updatePost(int postId, String title, String content) {
        try {
            service.updatePost(postId, title, content);
            return ApiResponse.ok();
        } catch (GeneralException e) {
            return ApiResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            return ApiResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse<Void> deletePost(int postId) {
        try {
            service.deletePost(postId);
            return ApiResponse.ok();
        } catch (GeneralException e) {
            return ApiResponse.fail(e.getErrorCode());
        } catch (Exception e) {
            return ApiResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}