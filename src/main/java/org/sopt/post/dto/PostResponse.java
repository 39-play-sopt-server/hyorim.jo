package org.sopt.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;

public record PostResponse(
        Long id,

        @Schema(description = "카테고리", example = "IT")
        Category category,

        @Schema(description = "제목", example = "제목")
        String title,

        @Schema(description = "내용", example = "내용")
        String content,

        @Schema(description = "작성자", example = "닉네임")
        String author,

        @Schema(description = "조회수", example = "0")
        long viewCount
) {
    public static PostResponse from(Post post) {
        return new PostResponse(post.getId(), post.getCategory(), post.getTitle(), post.getContent(), post.getAuthor(), post.getViewCount());
    }
}
