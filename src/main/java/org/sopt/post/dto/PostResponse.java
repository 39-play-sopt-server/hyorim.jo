package org.sopt.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.community.domain.Prefix;
import org.sopt.post.domain.Post;

public record PostResponse(
        Long id,

        @Schema(description = "게시판", example = "자유게시판")
        String board,

        @Schema(description = "말머리", example = "질문")
        String prefix,

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
        Prefix prefix = post.getPrefix();
        return new PostResponse(
                post.getId(),
                post.getBoard().getName(),
                prefix == null ? null : prefix.getName(),
                post.getTitle(),
                post.getContent(),
                post.getCommunityProfile().getProfile().getName(),
                post.getViewCount()
        );
    }
}
