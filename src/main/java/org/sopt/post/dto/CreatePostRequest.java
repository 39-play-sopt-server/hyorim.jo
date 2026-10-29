package org.sopt.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreatePostRequest(
        @Schema(description = "게시판 ID", example = "1")
        Long boardId,

        @Schema(description = "말머리 ID", example = "1")
        Long prefixId,

        @Schema(description = "프로필 ID", example = "1")
        Long profileId,

        @Schema(description = "제목", example = "제목")
        String title,

        @Schema(description = "내용", example = "내용")
        String content
) {

}
