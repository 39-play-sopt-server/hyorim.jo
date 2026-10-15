package org.sopt.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdatePostRequest(
        @Schema(description = "제목", example = "제목")
        String title,

        @Schema(description = "내용", example = "내용")
        String content
) {
}
