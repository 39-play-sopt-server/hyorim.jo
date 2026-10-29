package org.sopt.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreatePostRequest(
        @Schema(description = "카테고리", example = "IT")
        String category,

        @Schema(description = "제목", example = "제목")
        String title,

        @Schema(description = "내용", example = "내용")
        String content,

        @Schema(description = "작성자", example = "닉네임")
        String author
) {

}
