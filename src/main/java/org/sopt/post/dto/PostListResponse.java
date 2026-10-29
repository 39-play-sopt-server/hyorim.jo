package org.sopt.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.post.domain.Post;

public record PostListResponse(
        Long id,

        @Schema(description = "카테고리", example = "IT")
        String category,

        @Schema(description = "제목", example = "제목")
        String title,

        @Schema(description = "작성자", example = "닉네임")
        String author,

        @Schema(description = "조회수", example = "0")
        long viewCount
) {
    public static PostListResponse from(Post post) {
        return new PostListResponse(post.getId(), post.getCategory().getName(), post.getTitle(), post.getAuthor(), post.getViewCount());
    }
}
