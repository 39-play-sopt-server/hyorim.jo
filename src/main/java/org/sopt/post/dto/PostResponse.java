package org.sopt.post.dto;

import org.sopt.post.domain.Post;

public record PostResponse(
        int id,
        String title,
        String content
) {
    public static PostResponse from(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContent());
    }
}
