package org.sopt.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.global.code.SuccessCode;
import org.sopt.global.response.ApiResponse;
import org.sopt.global.swagger.ApiSuccessCode;
import org.sopt.post.dto.CreatePostRequest;
import org.sopt.post.dto.PostListResponse;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.UpdatePostRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시글 API", description = "게시글과 관련된 API 입니다")
@RequestMapping(path = "/api/v1/posts")
public interface PostApi {
    @Operation(summary = "게시글 생성", description = "게시글을 생성합니다.")
    @PostMapping
    @ApiSuccessCode(SuccessCode.CREATED)
    ResponseEntity<ApiResponse<PostResponse>> createPost(
            @RequestBody CreatePostRequest request
    );

    @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 조회합니다. 카테고리를 지정하면 해당 카테고리의 글만 조회합니다.")
    @GetMapping
    ResponseEntity<ApiResponse<List<PostListResponse>>> getPosts(
            @Parameter(description = "페이지 번호", example = "0")
            @RequestParam(name = "page", defaultValue = "0") int page,

            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @Parameter(description = "카테고리")
            @RequestParam(name = "category", required = false) String category
    );

    @Operation(summary = "게시글 상세 조회", description = "게시글을 상세 조회합니다.")
    @GetMapping(path = "/{postId}")
    ResponseEntity<ApiResponse<PostResponse>> getPost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable(name = "postId") Long postId
    );

    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @PatchMapping(path = "/{postId}")
    ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable(name = "postId") Long postId,
            @RequestBody UpdatePostRequest request
    );

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @DeleteMapping(path = "/{postId}")
    ResponseEntity<ApiResponse<Void>> deletePost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable(name = "postId") Long postId
    );
}
