package org.sopt.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.community.domain.Prefix;
import org.sopt.post.domain.Post;

public record PostListResponse(
        Long id,

        @Schema(description = "말머리", example = "질문")
        String prefix,

        @Schema(description = "제목", example = "제목")
        String title,

        @Schema(description = "작성자", example = "닉네임")
        String author,

        @Schema(description = "조회수", example = "0")
        long viewCount
) {
    public static PostListResponse from(Post post) {
        Prefix prefix = post.getPrefix();
        return new PostListResponse(
                post.getId(),
                prefix == null ? null : prefix.getName(),
                post.getTitle(),
                post.getCommunityProfile().getProfile().getName(),
                post.getViewCount()
        );
    }
}
